package gr.uoa.ec.shopeeng.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.Util;
import gr.uoa.ec.shopeeng.models.Product;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductsFragment extends ListFragment {


    private static final String RESULTS_STRING = "SEARCH_RESULT";

    private FragmentManager fragmentManager;
    private Context applicationContext;


    ArrayList products = new ArrayList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }


        Log.i("FragmentLifecycle", "onCreateView");


    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.

        Bundle args = getArguments();
        products = args.getParcelableArrayList(RESULTS_STRING);

        if (args != null) {
            Log.i("SEARCH_RESULT", Arrays.toString(args.getParcelableArrayList("SEARCH_RESULT").toArray()));
            Log.i("Saved search results", Arrays.toString(products.toArray()));
        }

        // Create an array adapter for the list view, using the Ipsum headlines array
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, products));

        if (getFragmentManager().findFragmentById(R.id.fragment_container) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) parent.getAdapter().getItem(position);
                Log.i("retrieved product", product.toString());
            }
        });
    }


    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ArrayList getProducts() {
        return products;
    }

    public void setProducts(ArrayList products) {
        this.products = products;
    }
}
