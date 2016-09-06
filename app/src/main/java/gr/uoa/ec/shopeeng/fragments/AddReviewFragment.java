package gr.uoa.ec.shopeeng.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.models.Product;
import gr.uoa.ec.shopeeng.models.Review;
import gr.uoa.ec.shopeeng.models.Store;
import gr.uoa.ec.shopeeng.requests.AddReviewRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static gr.uoa.ec.shopeeng.utils.Constants.*;

public class AddReviewFragment extends Fragment {
    private String username;
    private Store store;
    private Product product;
    private String userLocation;

    private Button submit;
    private EditText comment;
    private RatingBar ratingBar;
    private Context applicationContext;
    private FragmentManager fragmentManager;

    public AddReviewFragment() {
    }


    public static AddReviewFragment newInstance(Context context, String username,
                                                Store store, Product product, String userLocation) {
        AddReviewFragment fragment = new AddReviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(STORE_RESULT, store);
        args.putParcelable(PRODUCT_RESULT, product);
        args.putString(USER_ID, username);
        args.putString(LOCATION, userLocation);
        fragment.setArguments(args);
        fragment.setApplicationContext(context);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            store = getArguments().getParcelable(STORE_RESULT);
            product = getArguments().getParcelable(PRODUCT_RESULT);
            username = getArguments().getString(USER_ID);
            userLocation = getArguments().getString(LOCATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_review, container, false);
        submit = (Button) view.findViewById(R.id.submitButton);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        comment = (EditText) view.findViewById(R.id.commentEditText);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Review review = new Review();
                review.setComment(comment.getText().toString());
                review.setRating(String.valueOf(ratingBar.getRating()));
                review.setUsername(username);
                review.setRdate(new SimpleDateFormat().format(Calendar.getInstance().getTime()));
                review.setStoreId(store.getStoreId());
                new AddReviewRequest(getApplicationContext(), getFragmentManager(), review,
                        store, product, userLocation, username).execute();
            }
        });
        return view;
    }


    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Context getApplicationContext() {
        return this.applicationContext;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
