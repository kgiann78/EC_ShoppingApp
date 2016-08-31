package gr.uoa.ec.shopeeng.requests;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.fragments.StoreFragment;
import gr.uoa.ec.shopeeng.models.*;
import gr.uoa.ec.shopeeng.utils.Util;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static gr.uoa.ec.shopeeng.utils.Constants.*;


public class AddReviewRequest extends AsyncTask<Void, Void, Void> {
    private Review review;
    private long rating;
    private int ratingsNumber;
    private FragmentManager fragmentManager;
    private Context applicationContext;


    public AddReviewRequest(Review review, int ratingsNumber, long rating, FragmentManager fragmentManager, Context applicationContext) {
        this.review = review;
        this.rating = rating;
        this.ratingsNumber = ratingsNumber;
        this.fragmentManager = fragmentManager;
        this.applicationContext = applicationContext;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

           // restTemplate.postForObject(buildCommentUrl(), review, Void.class);

        } catch (Exception e) {
            Log.e("AddReviewRequest", e.getMessage(), e);
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

        rating = (rating + review.getRating()) / ratingsNumber;

        args.putDouble(RATING_SCORE, rating);

        storeFragment.setArguments(args);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, storeFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    private String buildRatingUrl(String storeId) throws Exception {

        StringBuilder url = new StringBuilder();
        url.append(Util.getProperty("endpoint", applicationContext));
        url.append(Util.getProperty("ratings", applicationContext));

        Log.i("rating url", String.format(url.toString(), storeId));
        return String.format(url.toString(), storeId);

    }

    private String buildCommentUrl(String storeId) throws Exception {

        StringBuilder url = new StringBuilder();
        url.append(Util.getProperty("endpoint", applicationContext));
        url.append(Util.getProperty("addReview", applicationContext));
        Log.i("add review url", String.format(url.toString(), storeId));

        return String.format(url.toString(), storeId);

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
