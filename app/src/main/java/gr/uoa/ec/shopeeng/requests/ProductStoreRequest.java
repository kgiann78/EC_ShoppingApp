package gr.uoa.ec.shopeeng.requests;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import gr.uoa.ec.shopeeng.R;

import gr.uoa.ec.shopeeng.fragments.ProductStoresFragment;
import gr.uoa.ec.shopeeng.models.Product;
import gr.uoa.ec.shopeeng.models.Store;
import gr.uoa.ec.shopeeng.models.ProductStoreRequestObject;
import gr.uoa.ec.shopeeng.utils.Constants;
import gr.uoa.ec.shopeeng.utils.Util;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import static gr.uoa.ec.shopeeng.utils.Constants.*;


public class ProductStoreRequest extends AsyncTask<Void, Void, ArrayList<Store>> {

    private final String userId;
    private Product product;
    private ProductStoreRequestObject productStoreRequestObject;
    private FragmentManager fragmentManager;
    private Context applicationContext;


    public ProductStoreRequest(ProductStoreRequestObject productStoreRequestObject,
                               String userId,
                               Product product,
                               FragmentManager fragmentManager,
                               Context applicationContext) {
        this.product = product;
        this.userId = userId;
        this.productStoreRequestObject = productStoreRequestObject;
        this.fragmentManager = fragmentManager;
        this.applicationContext = applicationContext;
    }

    @Override
    protected ArrayList<Store> doInBackground(Void... params) {
        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ArrayList<Store> results = new ArrayList();
            results.addAll(Arrays.asList(restTemplate.getForObject((buildUrl(this.productStoreRequestObject)), Store[].class)));
            return results;
        } catch (Exception e) {
            Log.e(ProductStoreRequest.class.getName(), e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Store> stores) {
        // Create fragment and give it an argument specifying the article it should show
        ProductStoresFragment productStoresFragment = new ProductStoresFragment();
        productStoresFragment.setApplicationContext(applicationContext);
        productStoresFragment.setFragmentManager(fragmentManager);

        Bundle args = new Bundle();
        args.putParcelableArrayList(STORES_PRODUCT_RESULT, stores);
        args.putParcelable(SELECTED_PRODUCT, product);
        args.putString(Constants.USER_ID, userId);
        args.putString(DURATION, this.productStoreRequestObject.getDuration());
        args.putString(DISTANCE, this.productStoreRequestObject.getDistance());
        args.putString(UNITS, this.productStoreRequestObject.getUnit());
        args.putString(ORDER_BY, this.productStoreRequestObject.getOrderBy());
        args.putString(TRANSPORT_MODE, this.productStoreRequestObject.getTransportMode());

        args.putString(Constants.LOCATION, productStoreRequestObject.getUserLocation());
        productStoresFragment.setArguments(args);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, productStoresFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private String buildUrl(ProductStoreRequestObject requestObject) throws Exception {

        //search?keywords=%s&distance=%s&unit=%s&transportMode=%s&userLocation=%s&orderBy=%s
        StringBuilder url = new StringBuilder();
        url.append(Util.getProperty("endpoint", applicationContext));
        url.append(Util.getProperty("storesDistance", applicationContext));
        return String.format(url.toString(), requestObject.getProductName(), requestObject.getDistance(), requestObject.getUnit(), requestObject.getTransportMode(), requestObject.getUserLocation(), requestObject.getOrderBy());
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
