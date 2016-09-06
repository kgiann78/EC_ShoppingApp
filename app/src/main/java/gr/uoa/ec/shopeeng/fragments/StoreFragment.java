package gr.uoa.ec.shopeeng.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.adapters.ReviewAdapter;
import gr.uoa.ec.shopeeng.listeners.ShoppingListListener;
import gr.uoa.ec.shopeeng.models.Product;
import gr.uoa.ec.shopeeng.models.Review;
import gr.uoa.ec.shopeeng.models.Store;
import gr.uoa.ec.shopeeng.utils.Constants;

import java.util.ArrayList;

import static gr.uoa.ec.shopeeng.utils.Constants.*;

public class StoreFragment extends Fragment {
    private ShoppingListListener shoppingListListener;

    private FragmentManager fragmentManager;
    private Context applicationContext;
    private Store store;
    private Product product;
    private String userlocation;
    private String username;


    TextView storeName;
    TextView storeAddress;
    RatingBar ratingBar;
    TextView ratingScoreText;
    ImageButton addToShoppingList;
    ListView reviewsList;
    ArrayList<Review> reviews = new ArrayList<>();
    Double ratingScore = 0.00;
    Button directionsButton;
    Button redirectReviewButton;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store, container, false);
        storeName = (TextView) view.findViewById(R.id.store_name_text_view);
        ratingBar = (RatingBar) view.findViewById(R.id.store_rating_bar);
        storeAddress = (TextView) view.findViewById(R.id.store_address_text_view);
        ratingScoreText = (TextView) view.findViewById(R.id.rating_score_text_view);
        reviewsList = (ListView) view.findViewById(R.id.comments_list);
        addToShoppingList = (ImageButton) view.findViewById(R.id.addToShoppingListButton);
        directionsButton = (Button) view.findViewById(R.id.directionsButton);


        //TODO launches google maps on click to get directions
        directionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?&saddr=" + userlocation + "&daddr=" + storeAddress.getText().toString()));
                startActivity(intent);
            }
        });
        redirectReviewButton = (Button) view.findViewById(R.id.redirectReview);
        redirectReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddReviewFragment addReviewFragment = AddReviewFragment.newInstance(username, store.getStoreId());
                addReviewFragment.setFragmentManager(fragmentManager);
                addReviewFragment.setApplicationContext(applicationContext);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, addReviewFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getData();

        ratingScoreText.setText(getContext().getString(R.string.rating_score_value, ratingScore.toString(), reviews.size()));
        addToShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingListListener.onAddItemToShoppingListClicked(product, store);
            }
        });
        getListViews();


    }


    private void getData() {
        final Bundle args = getArguments();

        if (args != null) {
            reviews = args.getParcelableArrayList(Constants.REVIEWS_LIST);
            ratingScore = args.getDouble(Constants.RATING_SCORE);
            store = args.getParcelable(Constants.STORE_RESULT);
            product = args.getParcelable(Constants.PRODUCT_RESULT);
            userlocation = args.getString(LOCATION);

            storeName.setText(store.getName());
            storeAddress.setText(store.getAddress());
            ratingBar.setNumStars(RATING_STARS);
            ratingBar.setRating(ratingScore.floatValue() * RATING_STARS/MAXIMUM_RATING);

            username = args.getString(USER_ID);
        }
    }

    private void getListViews() {
        reviewsList.setAdapter(new ReviewAdapter(getContext(), reviews));
        if (getFragmentManager().findFragmentById(R.id.fragment_container) != null) {
            reviewsList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            shoppingListListener = (ShoppingListListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ShoppingListListener");
        }
    }


    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

}
