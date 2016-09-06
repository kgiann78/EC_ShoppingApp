package gr.uoa.ec.shopeeng.requests;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.utils.Constants;
import gr.uoa.ec.shopeeng.utils.Util;
import gr.uoa.ec.shopeeng.fragments.ProductsFragment;
import gr.uoa.ec.shopeeng.models.Product;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;


public class ProductRequest extends AsyncTask<Void, Void, ArrayList<Product>> {
    private final String username;
    private String searchText;
    private FragmentManager fragmentManager;
    private Context applicationContext;


    public ProductRequest(String keywords, String username, FragmentManager fragmentManager, Context applicationContext) {
        this.searchText = keywords;
        this.username = username;
        this.fragmentManager = fragmentManager;
        this.applicationContext = applicationContext;
    }

    @Override
    protected ArrayList<Product> doInBackground(Void... params) {
        try {

            //+ searchText;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ArrayList results = new ArrayList();
            results.addAll(Arrays.asList(restTemplate.getForObject((buildUrl(this.searchText)), Product[].class)));

            return results;

        } catch (Exception e) {
            Log.e(ProductRequest.class.getName(), e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Product> products) {

        // Create fragment and give it an argument specifying the article it should show
        ProductsFragment productsFragment = new ProductsFragment();
        productsFragment.setApplicationContext(applicationContext);
        productsFragment.setFragmentManager(fragmentManager);


        Bundle args = new Bundle();
        args.putString(Constants.SEARCH_TEXT, this.searchText);
        args.putParcelableArrayList(Constants.PRODUCT_RESULT, products);
        args.putString(Constants.USER_ID, username);

        productsFragment.setArguments(args);

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, productsFragment)
                .addToBackStack(null)
                .commit();
    }


    private String buildUrl(String keywords) throws Exception {
        String url = Util.getProperty("endpoint", applicationContext);
        url += Util.getProperty("products", applicationContext);
        return String.format(url, keywords);
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
