package gr.uoa.ec.shopeeng.fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.MediaRouteButton;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.adapters.ProductAdapter;
import gr.uoa.ec.shopeeng.listeners.OnAddToShoppingListListener;
import gr.uoa.ec.shopeeng.models.Product;
import gr.uoa.ec.shopeeng.models.ProductStoreRequestObject;
import gr.uoa.ec.shopeeng.requests.ProductStoreRequest;
import gr.uoa.ec.shopeeng.utils.Constants;
import gr.uoa.ec.shopeeng.utils.ShoppingLocationListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProductsFragment extends Fragment {

    private FragmentManager fragmentManager;
    private Context applicationContext;

    private ListView productsList;
    private TextView searchText;
    private ArrayList<Product> products;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        productsList = (ListView) view.findViewById(R.id.product_list);
        searchText = (TextView) view.findViewById(R.id.search_text_editText);
        products = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        final Bundle args = getArguments();

        if (args != null) {
            products = args.getParcelableArrayList(Constants.PRODUCT_RESULT);
            searchText.setText(args.getString(Constants.SEARCH_TEXT));
        }

        final ProductAdapter productAdapter = new ProductAdapter(getActivity(), products);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                productAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        productsList.setAdapter(productAdapter);
        if (getFragmentManager().findFragmentById(R.id.fragment_container) != null) {
            productsList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }

        productsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) parent.getAdapter().getItem(position);

                //TODO: use user location to search for stores for each product
                String userLocation = "Εθνικής Αντιστάσεως 48, Χαλάνδρι";
                String distance = "100";
                String duration = "-1";
                String unit = "KM";
                String orderBy = "DISTANCE";
                String transportMode = "DRIVING";

                //TODO: just for testing - going to clean this up later
                new ProductStoreRequest(
                        new ProductStoreRequestObject(product.getName(), userLocation, distance, duration, unit, orderBy, transportMode),
                        product, fragmentManager, applicationContext).execute();
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

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

}
