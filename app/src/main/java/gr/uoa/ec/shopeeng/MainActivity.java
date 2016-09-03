package gr.uoa.ec.shopeeng;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import gr.uoa.ec.shopeeng.fragments.ProductsFragment;
import gr.uoa.ec.shopeeng.fragments.SearchFragment;
import gr.uoa.ec.shopeeng.fragments.ShoppingListFragment;
import gr.uoa.ec.shopeeng.listeners.OnAddToShoppingListListener;
import gr.uoa.ec.shopeeng.listeners.OnSearchClickedListener;
import gr.uoa.ec.shopeeng.models.Product;
import gr.uoa.ec.shopeeng.models.ShoppingItem;
import gr.uoa.ec.shopeeng.models.ShoppingList;
import gr.uoa.ec.shopeeng.models.Store;
import gr.uoa.ec.shopeeng.requests.ProductRequest;
import gr.uoa.ec.shopeeng.utils.Constants;
import gr.uoa.ec.shopeeng.utils.ShoppingListManager;
import gr.uoa.ec.shopeeng.utils.ShoppingLocationListener;

import java.util.*;

public class MainActivity extends AppCompatActivity implements OnSearchClickedListener, OnAddToShoppingListListener {

    private ShoppingListManager shoppingListManager;
    LocationManager locationManager;
    ShoppingLocationListener locationListener;

    String longitude = "0.0";
    String latitude = "0.0";

    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private static final int INITIAL_REQUEST = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationSupport();

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            SearchFragment searchFragment = new SearchFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            searchFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, searchFragment).commit();
        }

        shoppingListManager = new ShoppingListManager();
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName("My shopping list");
        shoppingList.setCreationDate(new Date());
        shoppingListManager.setShoppingList(shoppingList);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "longitude: " + longitude + "latitude :" + latitude, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_show_shopping_list) {
            ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
            Bundle args = new Bundle();

            ArrayList<ShoppingItem> shoppingItems = shoppingListManager.getShoppingItems();

            args.putParcelableArrayList(Constants.ITEMS_IN_SHOPPING_LIST, shoppingItems);
            shoppingListFragment.setArguments(args);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, shoppingListFragment)
                    .addToBackStack(null).commit();

        } else if (id == R.id.action_go_to_search) {
            SearchFragment searchFragment = new SearchFragment();
            searchFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, searchFragment)
                    .addToBackStack(null).commit();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchClicked(String searchText) {

        try {
            new ProductRequest(searchText, getSupportFragmentManager(), getApplicationContext()).execute();

        } catch (Exception e) {
            Log.e(MainActivity.class.toString(), e.getMessage());
        }
    }

    @Override
    public void onAddProductToShoppingClicked(Product product, Store store) {
        try {
            if (shoppingListManager.productAlreadyInList(product) && store == null) {
                Toast.makeText(getApplicationContext(), "Χμμμ! Υπάρχει ήδη στη λίστα!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                shoppingListManager.addProduct(product, store);
            }
        } catch (Exception e) {
            Log.e(MainActivity.class.toString(), e.getMessage());
            return;
        }

        if (store != null)
            Toast.makeText(getApplicationContext(), "Ωραία! Τρέχα να το πάρεις!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Μπράβο! Το έβαλες στη λίστα! 'Ελα να βρούμε μαγαζάκι!", Toast.LENGTH_SHORT).show();
    }


    @TargetApi(Build.VERSION_CODES.M)
    void locationSupport() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            requestPermissions(LOCATION_PERMS, INITIAL_REQUEST);
            // here to request the missing permissions, and then overriding
            // public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                        int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.i(MainActivity.class.getName(), "gpsStatus=" + gpsStatus);

        locationListener = new ShoppingLocationListener();
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        if (locationListener.getLocation() != null) {
            longitude = locationListener.getLocation().getLongitude() + "";
            latitude = locationListener.getLocation().getLatitude() + "";
        }

//                Location location = new Location("network");
//                location.setLatitude(-15.83554363);
//                location.setLongitude(-48.01770782);
//                location.setTime(new Date().getTime());
//                location.setAccuracy(100.0f);
//                location.setElapsedRealtimeNanos(System.nanoTime());
//
//                locationManager.addTestProvider(LocationManager.GPS_PROVIDER, false, false, false, false, true, true, true, Criteria.POWER_LOW, Criteria.ACCURACY_FINE);
//                locationManager.setTestProviderStatus(LocationManager.GPS_PROVIDER, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
//                locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
//                locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, location);
    }
}
