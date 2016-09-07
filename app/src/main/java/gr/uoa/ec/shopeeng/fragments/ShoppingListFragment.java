package gr.uoa.ec.shopeeng.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;


import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.adapters.ShoppingItemAdapter;
import gr.uoa.ec.shopeeng.listeners.LocationUpdateListener;
import gr.uoa.ec.shopeeng.listeners.ShoppingListListener;
import gr.uoa.ec.shopeeng.listeners.ShoppingLocationListener;
import gr.uoa.ec.shopeeng.models.ProductStoreRequestObject;
import gr.uoa.ec.shopeeng.models.ShoppingItem;
import gr.uoa.ec.shopeeng.requests.ProductStoreRequest;
import gr.uoa.ec.shopeeng.requests.ReviewRequest;
import gr.uoa.ec.shopeeng.utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static gr.uoa.ec.shopeeng.utils.Constants.USER_ID;

public class ShoppingListFragment extends Fragment {
    private ShoppingListListener shoppingListListener;

    private LocationManager locationManager;
    private ShoppingLocationListener locationListener;
    private Location location;
    private String username;
    private String userLocation;

    private SwipeMenuListView shoppingListView;

    public ShoppingListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        shoppingListView = (SwipeMenuListView) view.findViewById(R.id.shopping_list);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(getContext());
                openItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                openItem.setWidth(dp2px(90));
                openItem.setTitle("Delete");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);
            }
        };

        shoppingListView.setMenuCreator(creator);
        shoppingListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        if (getArguments() != null) {
            Bundle args = getArguments();
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

        this.locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
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

        shoppingListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                ShoppingItem shoppingItem = (ShoppingItem) shoppingListView.getAdapter().getItem(position);
                shoppingListListener.onDeleteItemFromShoppingListClicked(shoppingItem.getProduct());
                // false : close the menu; true : not close the menu
                return false;
            }
        });

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

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            shoppingListListener = (ShoppingListListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ShoppingListListener");
        }
    }
}
