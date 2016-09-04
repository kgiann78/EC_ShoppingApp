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
import android.widget.*;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.adapters.StoreAdapter;
import gr.uoa.ec.shopeeng.listeners.OnAddToShoppingListListener;
import gr.uoa.ec.shopeeng.models.Product;
import gr.uoa.ec.shopeeng.models.Store;
import gr.uoa.ec.shopeeng.requests.ReviewRequest;
import gr.uoa.ec.shopeeng.utils.OrderBy;
import gr.uoa.ec.shopeeng.utils.TransportMode;
import gr.uoa.ec.shopeeng.utils.Units;

import java.util.ArrayList;
import java.util.List;

import static gr.uoa.ec.shopeeng.utils.Constants.*;

public class ProductStoresFragment extends Fragment {

    private OnAddToShoppingListListener addToShoppingListListener;

    private FragmentManager fragmentManager;
    private Context applicationContext;
    private String userId;
    ListView storesList;
    TextView productName;
    TextView productDetails;
    ImageButton addToShoppingList;

    RadioButton priceRadioButton;
    RadioButton distanceRadioButton;
    EditText inputDistanceEditText;
    Switch transportModeSwitch;
    Switch unitsSwitch;

    List<Store> stores;
    Product product;
    String location;

    String duration = "";
    String distance = "";
    Units units = Units.KM;
    OrderBy orderBy = OrderBy.DISTANCE;
    TransportMode transportMode = TransportMode.DRIVING;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productstore, container, false);
        storesList = (ListView) view.findViewById(R.id.storesListView);
        productName = (TextView) view.findViewById(R.id.productNameTextView);
        productDetails = (TextView) view.findViewById(R.id.productDetailsTextView);
        addToShoppingList = (ImageButton) view.findViewById(R.id.addToShoppingListButton);

        priceRadioButton = (RadioButton) view.findViewById(R.id.priceRadioButton);
        distanceRadioButton = (RadioButton) view.findViewById(R.id.distanceRadioButton);
        inputDistanceEditText = (EditText) view.findViewById(R.id.inputDistanceEditText);
        transportModeSwitch = (Switch) view.findViewById(R.id.transportModeSwitch);
        unitsSwitch = (Switch) view.findViewById(R.id.unitsSwitch);

        stores = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        //get data from bundles
        Bundle args = getArguments();
        if (args != null) {
            stores = args.getParcelableArrayList(STORES_PRODUCT_RESULT);
            product = args.getParcelable(SELECTED_PRODUCT);
            productName.setText(product.getName());
            productDetails.setText(product.getDescription());

            duration = args.getString(DURATION);
            distance = args.getString(DISTANCE);

            units = Units.valueOf(args.getString(UNITS));
            orderBy = OrderBy.valueOf(args.getString(ORDER_BY));
            transportMode = TransportMode.valueOf(args.getString(TRANSPORT_MODE));

            location = args.getString(LOCATION);
            userId = args.getString(USER_ID);

        }

        setSelections();

        //show list of stores
        getStoresListView();

        //catch event when user clicks on a store name-> redirect to store page
        // for rating, comments and store info

        addToShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToShoppingListListener.onAddProductToShoppingClicked(product, null);
            }
        });

        storesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Store store = (Store) parent.getAdapter().getItem(position);
                Log.i("clicked store", store.toString());
                //add request for reviews and reviews here!!
                new ReviewRequest(store, product, location, userId,getFragmentManager(), applicationContext).execute();
            }
        });
    }

    private void setSelections() {
        inputDistanceEditText.setText(distance);

        switch (orderBy) {
            case DISTANCE:
                distanceRadioButton.setChecked(true);
                break;
            case PRICE:
                priceRadioButton.setChecked(true);
                break;
            default:
                break;
        }

        switch (transportMode) {
            case DRIVING:
                transportModeSwitch.setChecked(false);
                break;
            case WALKING:
                transportModeSwitch.setChecked(true);
                break;
            default:
                break;
        }

        switch (units) {
            case KM:
                unitsSwitch.setChecked(false);
                break;
            case HOURS:
                unitsSwitch.setChecked(true);
                break;
            default:
                break;
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


}
