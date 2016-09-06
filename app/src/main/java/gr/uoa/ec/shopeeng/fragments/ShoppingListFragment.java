package gr.uoa.ec.shopeeng.fragments;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.adapters.ShoppingItemAdapter;
import gr.uoa.ec.shopeeng.listeners.LocationUpdateListener;
import gr.uoa.ec.shopeeng.listeners.ShoppingLocationListener;
import gr.uoa.ec.shopeeng.models.ProductStoreRequestObject;
import gr.uoa.ec.shopeeng.models.ShoppingItem;
import gr.uoa.ec.shopeeng.models.ShoppingList;
import gr.uoa.ec.shopeeng.requests.ProductStoreRequest;
import gr.uoa.ec.shopeeng.requests.ReviewRequest;
import gr.uoa.ec.shopeeng.utils.Constants;
import gr.uoa.ec.shopeeng.utils.OrderBy;
import gr.uoa.ec.shopeeng.utils.TransportMode;
import gr.uoa.ec.shopeeng.utils.Units;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static gr.uoa.ec.shopeeng.utils.Constants.USER_ID;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShoppingListFragment extends Fragment {

    private LocationManager locationManager;
    private ShoppingLocationListener locationListener;
    private Location location;
    private String username;
    private String userLocation;


    private ListView shoppingListView;
    public ShoppingListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        if (getArguments() != null) {
            Bundle args = getArguments();
            shoppingListView = (ListView) view.findViewById(R.id.shopping_list);
            ArrayList<ShoppingItem> shoppingItems = args.getParcelableArrayList(Constants.ITEMS_IN_SHOPPING_LIST);

            shoppingListView.setAdapter(new ShoppingItemAdapter(getActivity(), shoppingItems));
            shoppingListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {
            username = args.getString(USER_ID);
        }

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new ShoppingLocationListener();
        locationListener.onLocationUpdated(new LocationUpdateListener() {
            @Override
            public void onLocationUpdated(Location location) {
                ShoppingListFragment.this.location = location;
            }
        });

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

        userLocation = "Εθνικής Αντιστάσεως 48, Χαλάνδρι";
        if (addresses.size() > 0) {
            userLocation = addresses.get(0).getAddressLine(0);
            userLocation += ", " + addresses.get(0).getPostalCode();
        }

        shoppingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShoppingItem shoppingItem = (ShoppingItem) adapterView.getAdapter().getItem(i);

                if (shoppingItem.getStore() != null) {
                    new ReviewRequest(shoppingItem.getStore(), shoppingItem.getProduct(), userLocation, username, getFragmentManager(), getContext()).execute();
                } else {
                    String distance = "100";
                    String duration = "-1";
                    String unit = Units.KM.name();
                    String orderBy = OrderBy.DISTANCE.name();
                    String transportMode = TransportMode.DRIVING.name();
                    new ProductStoreRequest(
                            new ProductStoreRequestObject(shoppingItem.getProduct().getName(),
                                    userLocation, distance, duration, unit, orderBy, transportMode),
                            username, shoppingItem.getProduct(), getFragmentManager(), getContext()).execute();
                }
            }
        });
    }
}
