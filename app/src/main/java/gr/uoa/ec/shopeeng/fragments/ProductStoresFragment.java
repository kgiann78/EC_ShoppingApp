package gr.uoa.ec.shopeeng.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.adapters.StoreAdapter;
import gr.uoa.ec.shopeeng.listeners.AddToShoppingListListener;
import gr.uoa.ec.shopeeng.listeners.FilterChangedListener;
import gr.uoa.ec.shopeeng.models.Product;
import gr.uoa.ec.shopeeng.models.ProductStoreRequestObject;
import gr.uoa.ec.shopeeng.models.Store;
import gr.uoa.ec.shopeeng.requests.ProductStoreRequest;
import gr.uoa.ec.shopeeng.requests.ReviewRequest;
import gr.uoa.ec.shopeeng.utils.OrderBy;
import gr.uoa.ec.shopeeng.utils.TransportMode;
import gr.uoa.ec.shopeeng.utils.Units;

import java.util.ArrayList;
import java.util.List;

import static gr.uoa.ec.shopeeng.utils.Constants.*;

public class ProductStoresFragment extends Fragment {
    private FilterChangedListener filterChangedListener;

    private AddToShoppingListListener addToShoppingListListener;

    private FragmentManager fragmentManager;
    private Context applicationContext;
    private String userId;
    private ListView storesList;
    private TextView productName;
    private TextView productDetails;
    private ImageButton addToShoppingList;

    private RadioButton priceRadioButton;
    private RadioButton distanceRadioButton;
    private EditText inputDistanceEditText;
    private Switch transportModeSwitch;
    private Switch unitsSwitch;

    private List<Store> stores;
    private Product product;

    private String location;
    private String duration;
    private String distance;
    private Units units;
    private OrderBy orderBy;
    private TransportMode transportMode;

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

        location = "";
        duration = "";
        distance = "";
        stores = new ArrayList<>();
        units = Units.KM;
        orderBy = OrderBy.DISTANCE;
        transportMode = TransportMode.DRIVING;
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

        filterChangedListener = new FilterChangedListener() {
            @Override
            public void onFilterChangedListener() {
                new ProductStoreRequest(
                        new ProductStoreRequestObject(product.getName(), location, inputDistanceEditText.getText().toString(), duration, units.name(), orderBy.name(), transportMode.name()), userId,
                        product, fragmentManager, applicationContext).execute();
            }
        };

        setSelections();

        filtering();


        //show list of stores
        getStoresListView();

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
                new ReviewRequest(store, product, location, userId, getFragmentManager(), applicationContext).execute();
            }
        });
    }

    private void filtering() {

        inputDistanceEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((i == EditorInfo.IME_ACTION_DONE) || ((keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (keyEvent.getAction() == KeyEvent.ACTION_DOWN))) {

                    Toast.makeText(getContext(), "Distance: " + textView.getText().toString(), Toast.LENGTH_SHORT).show();

                    filterChangedListener.onFilterChangedListener();

                    return true;
                } else {
                    return false;
                }
            }
        });

        transportModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    transportMode = TransportMode.WALKING;
                } else
                    transportMode = TransportMode.DRIVING;

                Toast.makeText(getContext(), transportMode.name(), Toast.LENGTH_SHORT).show();

                filterChangedListener.onFilterChangedListener();
            }
        });

        unitsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    units = Units.HOURS;
                    orderBy = OrderBy.DISTANCE;
                } else {
                    units = Units.KM;
                    orderBy = OrderBy.DISTANCE;
                }

                Toast.makeText(getContext(), units.name(), Toast.LENGTH_SHORT).show();

                new ProductStoreRequest(
                        new ProductStoreRequestObject(product.getName(), location, inputDistanceEditText.getText().toString(), duration, units.name(), orderBy.name(), transportMode.name()), userId,
                        product, fragmentManager, applicationContext).execute();
            }
        });

        priceRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!orderBy.equals(OrderBy.PRICE) && b) {
                    orderBy = OrderBy.PRICE;
                    transportModeSwitch.setEnabled(false);
                    unitsSwitch.setEnabled(false);

                    Toast.makeText(getContext(), compoundButton.getText(), Toast.LENGTH_SHORT).show();

                    filterChangedListener.onFilterChangedListener();
                }
            }
        });

        distanceRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!orderBy.equals(OrderBy.DISTANCE) && b) {
                    orderBy = OrderBy.DISTANCE;
                    transportModeSwitch.setEnabled(true);
                    unitsSwitch.setEnabled(true);

                    Toast.makeText(getContext(), compoundButton.getText(), Toast.LENGTH_SHORT).show();

                    filterChangedListener.onFilterChangedListener();
                }
            }
        });
    }

    private void setSelections() {
        inputDistanceEditText.setText(distance);

        switch (orderBy) {
            case DISTANCE:
                distanceRadioButton.setChecked(true);
                transportModeSwitch.setEnabled(true);
                unitsSwitch.setEnabled(true);
                break;
            case PRICE:
                priceRadioButton.setChecked(true);
                transportModeSwitch.setEnabled(false);
                unitsSwitch.setEnabled(false);
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
            addToShoppingListListener = (AddToShoppingListListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement AddToShoppingListListener");
        }
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }
}
