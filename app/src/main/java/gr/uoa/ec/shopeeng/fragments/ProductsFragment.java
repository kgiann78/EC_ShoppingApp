package gr.uoa.ec.shopeeng.fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import gr.uoa.ec.shopeeng.listeners.LocationUpdateListener;
import gr.uoa.ec.shopeeng.listeners.ShoppingLocationListener;
import gr.uoa.ec.shopeeng.models.Product;
import gr.uoa.ec.shopeeng.models.ProductStoreRequestObject;
import gr.uoa.ec.shopeeng.requests.ProductStoreRequest;
import gr.uoa.ec.shopeeng.utils.*;
import gr.uoa.ec.shopeeng.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductsFragment extends Fragment {

    private FragmentManager fragmentManager;
    private Context applicationContext;

    private ListView productsList;
    private TextView searchText;
    private ArrayList<Product> products;

    LocationManager locationManager;
    ShoppingLocationListener locationListener;

    private String userId;

    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int INITIAL_REQUEST = 1337;
    Location location;


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
        initializeLocationSupport();

        // During startup, check if there are arguments passed to the fragment.
        final Bundle args = getArguments();

        if (args != null) {
            userId = args.getString(Constants.USER_ID);
            products = args.getParcelableArrayList(Constants.PRODUCT_RESULT);
            searchText.setText(args.getString(Constants.SEARCH_TEXT));

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

                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    List<Address> addresses = new ArrayList<Address>();
                    if (location != null) {
                        try {
                            Log.i(ProductsFragment.class.getName(), location.toString());
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // initialize user location in case location hasnt been updated
                    String userLocation = "Εθνικής Αντιστάσεως 48, Χαλάνδρι";
                    if (addresses.size() > 0) {
                        userLocation = addresses.get(0).getAddressLine(0);
                        userLocation += ", " + addresses.get(0).getPostalCode();
                    }
                    Log.i(ProductsFragment.class.getName(), userLocation);

                    String distance = "100";
                    String duration = "-1";
                    String unit = Units.KM.name();
                    String orderBy = OrderBy.DISTANCE.name();
                    String transportMode = TransportMode.DRIVING.name();

                    new ProductStoreRequest(
                            new ProductStoreRequestObject(product.getName(), userLocation, distance, duration, unit, orderBy, transportMode), userId,
                            product, fragmentManager, applicationContext).execute();
                }
            });

        }
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


    @TargetApi(Build.VERSION_CODES.M)
    void initializeLocationSupport() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            requestPermissions(LOCATION_PERMS, INITIAL_REQUEST);
            // here to request the missing permissions, and then overriding
            // public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                        int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        locationListener = new ShoppingLocationListener();
        locationListener.onLocationUpdated(new LocationUpdateListener() {
            @Override
            public void onLocationUpdated(Location location) {
                ProductsFragment.this.location = location;
                Toast.makeText(getContext(), ProductsFragment.this.location.getLatitude() + " " + ProductsFragment.this.location.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        });

//        Single location request
//        Looper looper = Looper.myLooper();
//        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, looper);

//        Periodic location request
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 10000, 0, locationListener);
    }

}
