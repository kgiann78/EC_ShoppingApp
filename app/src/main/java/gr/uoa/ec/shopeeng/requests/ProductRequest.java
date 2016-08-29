package gr.uoa.ec.shopeeng.requests;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.utils.Util;
import gr.uoa.ec.shopeeng.fragments.ProductsFragment;
import gr.uoa.ec.shopeeng.models.Product;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import static gr.uoa.ec.shopeeng.utils.Constants.PRODUCT_RESULT;


public class ProductRequest extends AsyncTask<Void, Void, ArrayList<Product>> {
    private String searchText;
    private FragmentManager fragmentManager;
    private Context applicationContext;


    public ProductRequest(String keywords, FragmentManager fragmentManager, Context applicationContext) {
        this.searchText = keywords;
        this.fragmentManager = fragmentManager;
        this.applicationContext = applicationContext;
    }

    @Override
    protected ArrayList doInBackground(Void... params) {
        try {

            //+ searchText;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ArrayList results = new ArrayList();
            Log.e("res string", Arrays.toString(restTemplate.getForObject(buildUrl(this.searchText), Product[].class)));

            results.addAll(Arrays.asList(restTemplate.getForObject((buildUrl(this.searchText)), Product[].class)));
            Log.e("res list", Arrays.toString(results.toArray()));

            return results;

        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList products) {

        // Create fragment and give it an argument specifying the article it should show
        ProductsFragment productsFragment = new ProductsFragment();
        productsFragment.setApplicationContext(applicationContext);
        productsFragment.setFragmentManager(fragmentManager);


        Bundle args = new Bundle();
        Log.e("parcelable list", Arrays.toString(products.toArray()));

        args.putParcelableArrayList(PRODUCT_RESULT, products);
        productsFragment.setArguments(args);

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, productsFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }


    private String buildUrl(String keywords) throws Exception {

        StringBuilder url = new StringBuilder();
        url.append(Util.getProperty("endpoint", applicationContext));
        url.append(Util.getProperty("products", applicationContext));
        return String.format(url.toString(), keywords);

    }


    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
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
