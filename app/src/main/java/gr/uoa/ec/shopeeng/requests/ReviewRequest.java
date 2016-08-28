package gr.uoa.ec.shopeeng.requests;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.fragments.ProductsFragment;
import gr.uoa.ec.shopeeng.fragments.StoreFragment;
import gr.uoa.ec.shopeeng.models.*;
import gr.uoa.ec.shopeeng.utils.Constants;
import gr.uoa.ec.shopeeng.utils.Util;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import static gr.uoa.ec.shopeeng.utils.Constants.*;


public class ReviewRequest extends AsyncTask<Void, Void, ReviewData> {
    private Store store;
    private FragmentManager fragmentManager;
    private Context applicationContext;


    public ReviewRequest(Store store, FragmentManager fragmentManager, Context applicationContext) {
        this.store = store;
        this.fragmentManager = fragmentManager;
        this.applicationContext = applicationContext;
    }

    @Override
    protected ReviewData doInBackground(Void... params) {
        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            ReviewData reviewData = new ReviewData();

            Comment[] commentResults = restTemplate.getForObject((buildCommentUrl(store.getStoreId())), Comment[].class);
            Rating[] ratingResults = restTemplate.getForObject((buildCommentUrl(store.getStoreId())), Rating[].class);

            reviewData.getComments().addAll(Arrays.asList(commentResults));
            reviewData.getRatings().addAll(Arrays.asList(ratingResults));

            return reviewData;

        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(ReviewData reviewData) {
        StoreFragment storeFragment = new StoreFragment();
        storeFragment.setApplicationContext(applicationContext);
        storeFragment.setFragmentManager(fragmentManager);
        Bundle args = new Bundle();

        args.putParcelableArrayList(COMMENTS_RESULTS, reviewData.getComments());
        args.putParcelableArrayList(RATING_RESULTS, reviewData.getRatings());
        args.putParcelable(STORE_RESULT, store);


        double ratingScore = 0.0;
        for (Object rating : reviewData.getRatings()) {
            ratingScore += Double.valueOf(((Rating) rating).getRating());
        }
        ratingScore = ratingScore / reviewData.getRatings().size();

        args.putDouble(RATING_SCORE, ratingScore);


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
        return String.format(url.toString(), storeId);

    }

    private String buildCommentUrl(String storeId) throws Exception {

        StringBuilder url = new StringBuilder();
        url.append(Util.getProperty("endpoint", applicationContext));
        url.append(Util.getProperty("comments", applicationContext));
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
