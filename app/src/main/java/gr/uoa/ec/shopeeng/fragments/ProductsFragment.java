package gr.uoa.ec.shopeeng.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationServices;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.listeners.OnAddToShoppingListListener;
import gr.uoa.ec.shopeeng.listeners.OnSearchClickedListener;
import gr.uoa.ec.shopeeng.models.Product;
import gr.uoa.ec.shopeeng.models.StoreProductRequestObject;
import gr.uoa.ec.shopeeng.requests.ProductStoreRequest;
import gr.uoa.ec.shopeeng.utils.Constants;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductsFragment extends ListFragment {

    OnAddToShoppingListListener addToShoppingListListener;

    private FragmentManager fragmentManager;
    private Context applicationContext;


    ArrayList products = new ArrayList();
//    GoogleApiClient mGoogleApiClient;


    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Create an instance of GoogleAPIClient.

//        if (mGoogleApiClient == null) {
//            mGoogleApiClient = new GoogleApiClient.Builder(applicationContext)
//                    .addApi(LocationServices.API)
//                    .build();

//        }
//        ;


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

        final Bundle args = getArguments();

        if (args != null) {
            products = args.getParcelableArrayList(Constants.PRODUCT_RESULT);


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

                String userLocation = "Εθνικής Αντιστάσεως 48, Χαλάνδρι";
                String distance = "100";
                String duration = "-1";
                String unit = "KM";
                String orderBy = "DISTANCE";
                String transportMode = "DRIVING";

             //   mGoogleApiClient.connect();
              /*  if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }*/

             //   Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

               /* if (mLastLocation != null) {

                    String latitude = String.valueOf(mLastLocation.getLatitude());
                    String longitude = String.valueOf(mLastLocation.getLongitude());
                    userLocation = latitude + "," + longitude;
                }*/

                addToShoppingListListener.onAddProductToShoppingClicked(product);

                //TODO just for testing- going to clean thit up later
                new ProductStoreRequest(
                        new StoreProductRequestObject(product.getName(), userLocation, distance, duration, unit, orderBy, transportMode),
                        product, fragmentManager, applicationContext).execute();
            }

        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            addToShoppingListListener = (OnAddToShoppingListListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnAddToShoppingListListener");
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

    public void setProducts(ArrayList products) {
        this.products = products;
    }
}
