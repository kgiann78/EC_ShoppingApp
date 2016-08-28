package gr.uoa.ec.shopeeng.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.models.Product;
import gr.uoa.ec.shopeeng.models.Store;
import gr.uoa.ec.shopeeng.requests.ReviewRequest;

import java.util.ArrayList;
import java.util.Arrays;

import static gr.uoa.ec.shopeeng.utils.Constants.SELECTED_PRODUCT;
import static gr.uoa.ec.shopeeng.utils.Constants.STORES_PRODUCT_RESULT;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductStoresFragment extends ListFragment {


    private FragmentManager fragmentManager;
    private Context applicationContext;


    ArrayList stores = new ArrayList();
    Product product;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        //get data from bundles
        getData();

        //show item info
        //TODO - fix this so its displayed correctly
        //  getItemView();

        //show list of stores
        getStoresListView();

        //catch event when user clicks on a store name-> redirect to store page
        // for rating, comments and store info

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Store store = (Store) parent.getAdapter().getItem(position);
                Log.i("clicked store", store.toString());

                //add request for reviews and ratings here!!
                new ReviewRequest(store, getFragmentManager(), applicationContext).execute();

            }
        });
    }


    private void getData() {
        Bundle args = getArguments();
        stores = args.getParcelableArrayList(STORES_PRODUCT_RESULT);
        product = args.getParcelable(SELECTED_PRODUCT);

    }

    private void getItemView() {

        TextView ratingView = new TextView(applicationContext);
        ratingView.setText(product.toString());

        ViewGroup.LayoutParams layoutParams = new ViewPager.LayoutParams();
        getFragmentManager().findFragmentById(R.id.fragment_container).getActivity()
                .addContentView(ratingView, layoutParams);
    }

    private void getStoresListView() {

        //Get result list
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stores));
        if (getFragmentManager().findFragmentById(R.id.fragment_container) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }


}
