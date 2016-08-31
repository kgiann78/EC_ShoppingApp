package gr.uoa.ec.shopeeng.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.adapters.StoreAdapter;
import gr.uoa.ec.shopeeng.models.Product;
import gr.uoa.ec.shopeeng.models.Store;
import gr.uoa.ec.shopeeng.requests.ReviewRequest;

import java.util.ArrayList;
import java.util.List;

import static gr.uoa.ec.shopeeng.utils.Constants.SELECTED_PRODUCT;
import static gr.uoa.ec.shopeeng.utils.Constants.STORES_PRODUCT_RESULT;

public class ProductStoresFragment extends Fragment {


    private FragmentManager fragmentManager;
    private Context applicationContext;

    ListView storesList;
    TextView productName;
    TextView productDetails;
    List<Store> stores;
    Product product;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productstore, container, false);
        storesList = (ListView) view.findViewById(R.id.store_list);
        productName = (TextView) view.findViewById(R.id.product_name_text_view);
        productDetails = (TextView) view.findViewById(R.id.product_details_text_view);
        stores = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //get data from bundles
        getData();

        //TODO: show product information / store information correctly (rating -reviews etc)

        //show list of stores
        getStoresListView();

        //catch event when user clicks on a store name-> redirect to store page
        // for rating, comments and store info

        storesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        if (args != null) {
            stores = args.getParcelableArrayList(STORES_PRODUCT_RESULT);
            product = args.getParcelable(SELECTED_PRODUCT);
            productName.setText(product.getName());
            productDetails.setText(product.getDescription());
        }

    }

    private void getItemView() {

        TextView ratingView = new TextView(applicationContext);
        ratingView.setText(product.toString());

        ViewGroup.LayoutParams layoutParams = new ViewPager.LayoutParams();
        getFragmentManager().findFragmentById(R.id.fragment_container).getActivity()
                .addContentView(ratingView, layoutParams);
    }

    private void getStoresListView() {
        storesList.setAdapter(new StoreAdapter(getContext(), stores));
        if (getFragmentManager().findFragmentById(R.id.fragment_container) != null) {
            storesList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }


}
