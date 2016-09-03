package gr.uoa.ec.shopeeng.requests;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.fragments.StoreFragment;
import gr.uoa.ec.shopeeng.models.Review;
import gr.uoa.ec.shopeeng.utils.Constants;
import gr.uoa.ec.shopeeng.utils.Util;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static gr.uoa.ec.shopeeng.utils.Constants.RATING_SCORE;
import static gr.uoa.ec.shopeeng.utils.Constants.REVIEW;


public class AddReviewRequest extends AsyncTask<Void, Void, Void> {
    private Review review;
    private List<Review> existingReviews;
    private FragmentManager fragmentManager;
    private Context applicationContext;

    public AddReviewRequest(Review review, List<Review> existingReviews, FragmentManager fragmentManager, Context applicationContext) {
        this.review = review;
        this.existingReviews = existingReviews;
        this.fragmentManager = fragmentManager;
        this.applicationContext = applicationContext;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.postForObject(buildReviewUrl(), review, Void.class);

        } catch (Exception e) {
            Log.e("Added Review Request", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void c) {
        StoreFragment storeFragment = new StoreFragment();
        storeFragment.setApplicationContext(applicationContext);
        storeFragment.setFragmentManager(fragmentManager);

        Bundle args = new Bundle();
        args.putParcelable(REVIEW, review);

        existingReviews.add(review);

        args.putInt(Constants.REVIEWS_NUMBER, existingReviews.size());

        double rating = 0;
        for (Review r : existingReviews) {
            rating += Double.parseDouble(r.getRating());
        }
        rating = rating / existingReviews.size();

        args.putDouble(RATING_SCORE, rating);

        storeFragment.setArguments(args);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, storeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private String buildReviewUrl() throws Exception {
        StringBuilder url = new StringBuilder();
        url.append(Util.getProperty("endpoint", applicationContext));
        url.append(Util.getProperty("review", applicationContext));
        Log.i("review url", url.toString());
        return url.toString();
    }


    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }
}
