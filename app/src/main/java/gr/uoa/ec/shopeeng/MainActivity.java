package gr.uoa.ec.shopeeng;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import gr.uoa.ec.shopeeng.fragments.ProductsFragment;
import gr.uoa.ec.shopeeng.fragments.SearchFragment;
import gr.uoa.ec.shopeeng.fragments.ShoppingListFragment;
import gr.uoa.ec.shopeeng.listeners.OnAddToShoppingListListener;
import gr.uoa.ec.shopeeng.listeners.OnSearchClickedListener;
import gr.uoa.ec.shopeeng.models.Product;
import gr.uoa.ec.shopeeng.models.ShoppingList;
import gr.uoa.ec.shopeeng.requests.ProductRequest;
import gr.uoa.ec.shopeeng.utils.Constants;
import gr.uoa.ec.shopeeng.utils.ShoppingListManager;

import java.util.*;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements OnSearchClickedListener, OnAddToShoppingListListener {

    ShoppingListManager shoppingListManager;

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
        shoppingList.setName("My shopping list");
        shoppingList.setCreationDate(new Date());
        shoppingListManager.setShoppingList(shoppingList);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
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
        } else if (id == R.id.action_my_shopping_list) {
            ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
            Bundle args = new Bundle();

            Object[] productArray = shoppingListManager.getShoppingList()
                    .getProductsMap().keySet().toArray();

            ArrayList<Product> products = new ArrayList<>(productArray.length);
            for (Object object : productArray) {
                products.add((Product) object);
            }

            args.putParcelableArrayList(Constants.PRODUCTS_IN_SHOPPING_LIST, products);
            args.putString(Constants.SHOPPING_LIST_NAME, shoppingListManager.getShoppingList().getName());
            shoppingListFragment.setArguments(args);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, shoppingListFragment)
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
    public void onAddProductToShoppingClicked(Product product) {
        try {
            shoppingListManager.addProduct(product);
        } catch (Exception e) {
            Log.e(MainActivity.class.toString(), e.getMessage());
        }
    }
}
