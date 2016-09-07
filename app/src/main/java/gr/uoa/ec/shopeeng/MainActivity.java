package gr.uoa.ec.shopeeng;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import gr.uoa.ec.shopeeng.fragments.LoginAccountFragment;
import gr.uoa.ec.shopeeng.fragments.SearchFragment;
import gr.uoa.ec.shopeeng.fragments.ShoppingListFragment;
import gr.uoa.ec.shopeeng.listeners.LoginListener;
import gr.uoa.ec.shopeeng.listeners.SearchClickedListener;
import gr.uoa.ec.shopeeng.listeners.ShoppingListListener;
import gr.uoa.ec.shopeeng.models.Product;
import gr.uoa.ec.shopeeng.models.ShoppingItem;
import gr.uoa.ec.shopeeng.models.ShoppingList;
import gr.uoa.ec.shopeeng.models.Store;
import gr.uoa.ec.shopeeng.requests.AddToListRequest;
import gr.uoa.ec.shopeeng.requests.DeleteItemListRequest;
import gr.uoa.ec.shopeeng.requests.ProductRequest;
import gr.uoa.ec.shopeeng.utils.Constants;
import gr.uoa.ec.shopeeng.utils.ShoppingListManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchClickedListener, ShoppingListListener, LoginListener {
    private ShoppingListManager shoppingListManager;
    private String username = "Not Logged In";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

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
        shoppingListManager.setShoppingList(shoppingList);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.action_go_to_search) {
            SearchFragment searchFragment = new SearchFragment();
            searchFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, searchFragment)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.action_register_login) {
            LoginAccountFragment loginAccountFragment = LoginAccountFragment.newInstance(getApplicationContext());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, loginAccountFragment)
                    .addToBackStack(null)
                    .commit();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchClicked(String searchText) {

        try {
            new ProductRequest(searchText, username, getSupportFragmentManager(), getApplicationContext()).execute();

        } catch (Exception e) {
            Log.e(MainActivity.class.toString(), e.getMessage());
        }
    }

    @Override
    public void onAddItemToShoppingListClicked(Product product, Store store) {
        try {
            if (shoppingListManager.productAlreadyInList(product) && store == null) {
                Toast.makeText(getApplicationContext(), "Χμμμ! Υπάρχει ήδη στη λίστα!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                shoppingListManager.addProduct(product, store);
                new AddToListRequest(username, product.getProductId(), store.getStoreId(),
                        store.getPrice(), getApplicationContext()).execute();
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

    @Override
    public void onDeleteItemFromShoppingListClicked(Product product, Store store) {
        //TODO implement call to delete item from shopping list
        try {
            shoppingListManager.removeProduct(product);
            new DeleteItemListRequest(username, product.getProductId(), store.getStoreId(),
                    getApplicationContext()).execute();

        } catch (Exception e) {
            Log.e(MainActivity.class.toString(), e.getMessage());
            return;
        }
    }

    @Override
    public void onSuccessfullLogin(String username) {
        this.username = username;

        //shoppingListManager.initList();
    }

}
