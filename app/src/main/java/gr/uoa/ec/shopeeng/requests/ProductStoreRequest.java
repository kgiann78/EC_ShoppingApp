package gr.uoa.ec.shopeeng.requests;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import gr.uoa.ec.shopeeng.R;

import gr.uoa.ec.shopeeng.fragments.ProductStoresFragment;
import gr.uoa.ec.shopeeng.fragments.ProductsFragment;
import gr.uoa.ec.shopeeng.models.Product;
import gr.uoa.ec.shopeeng.models.Store;
import gr.uoa.ec.shopeeng.models.StoreProductRequestObject;
import gr.uoa.ec.shopeeng.utils.Util;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import static gr.uoa.ec.shopeeng.utils.Constants.SELECTED_PRODUCT;
import static gr.uoa.ec.shopeeng.utils.Constants.STORES_PRODUCT_RESULT;


public class ProductStoreRequest extends AsyncTask<Void, Void, ArrayList<Product>> {

    private Product product;
    private StoreProductRequestObject storeProductRequestObject;
    private FragmentManager fragmentManager;
    private Context applicationContext;


    public ProductStoreRequest(StoreProductRequestObject storeProductRequestObject, Product product, FragmentManager fragmentManager, Context applicationContext) {
        this.product = product;
        this.storeProductRequestObject = storeProductRequestObject;
        this.fragmentManager = fragmentManager;
        this.applicationContext = applicationContext;
    }

    @Override
    protected ArrayList doInBackground(Void... params) {
        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ArrayList results = new ArrayList();

            results.addAll(Arrays.asList(restTemplate.getForObject((buildUrl(this.storeProductRequestObject)), Store[].class)));
            Log.e("Stores list", Arrays.toString(results.toArray()));

            return results;

        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList stores) {

        // Create fragment and give it an argument specifying the article it should show
        ProductStoresFragment productsFragment = new ProductStoresFragment();
        productsFragment.setApplicationContext(applicationContext);
        productsFragment.setFragmentManager(fragmentManager);


        Bundle args = new Bundle();
        Log.e("parcelable store list", Arrays.toString(stores.toArray()));
        args.putParcelableArrayList(STORES_PRODUCT_RESULT, stores);
        args.putParcelable(SELECTED_PRODUCT, product);
        productsFragment.setArguments(args);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, productsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private String buildUrl(StoreProductRequestObject requestObject) throws Exception {

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
