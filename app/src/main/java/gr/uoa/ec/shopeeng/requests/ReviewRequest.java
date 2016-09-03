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
import java.util.List;

import static gr.uoa.ec.shopeeng.utils.Constants.*;


public class ReviewRequest extends AsyncTask<Void, Void, ArrayList<Review>> {
    private Store store;
    private Product product;
    private String userlocation;
    private FragmentManager fragmentManager;
    private Context applicationContext;


    public ReviewRequest(Store store, Product product, String userlocation, FragmentManager fragmentManager, Context applicationContext) {
        this.store = store;
        this.product = product;
        this.userlocation = userlocation;
        this.fragmentManager = fragmentManager;
        this.applicationContext = applicationContext;
    }

    @Override
    protected ArrayList<Review> doInBackground(Void... params) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ArrayList reviews = new ArrayList();
            reviews.addAll(Arrays.asList(restTemplate.getForObject((buildReviewsUrl(store.getStoreId())), Review[].class)));
            return reviews;
        } catch (Exception e) {
            Log.e(ReviewRequest.class.getName(), e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Review> reviews) {
        StoreFragment storeFragment = new StoreFragment();
        storeFragment.setApplicationContext(applicationContext);
        storeFragment.setFragmentManager(fragmentManager);

        Bundle args = new Bundle();

        args.putParcelableArrayList(REVIEWS_LIST, reviews);

        args.putInt(Constants.REVIEWS_NUMBER, reviews.size());

        //getRating score
        double rating = 0;
        for (Review r : reviews) {
            rating += Double.valueOf(r.getRating());
        }
        rating = rating / reviews.size();
        args.putDouble(RATING_SCORE, rating);

        //add product and store data
        args.putParcelable(STORE_RESULT, store);
        args.putParcelable(PRODUCT_RESULT, product);
        args.putString(LOCATION, userlocation);

        storeFragment.setArguments(args);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, storeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private String buildReviewsUrl(String storeId) throws Exception {
        StringBuilder url = new StringBuilder();
        url.append(Util.getProperty("endpoint", applicationContext));
        url.append(Util.getProperty("getReviews", applicationContext));
        Log.i("rating url", String.format(url.toString(), storeId));
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
