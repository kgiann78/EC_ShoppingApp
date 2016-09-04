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
import gr.uoa.ec.shopeeng.models.Review;
import gr.uoa.ec.shopeeng.requests.AddReviewRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static gr.uoa.ec.shopeeng.utils.Constants.STORE_ID;
import static gr.uoa.ec.shopeeng.utils.Constants.USER_ID;

public class AddReviewFragment extends Fragment {
    private String userId;
    private String storeId;

    private Button submit;
    private EditText comment;
    private RatingBar ratingBar;

    private Context applicationContext;
    private FragmentManager fragmentManager;

    public AddReviewFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static AddReviewFragment newInstance(String userId, String storeId) {
        AddReviewFragment fragment = new AddReviewFragment();
        Bundle args = new Bundle();
        args.putString(STORE_ID, storeId);
        args.putString(USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeId = getArguments().getString(STORE_ID);
            userId = getArguments().getString(USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_review, container, false);
        submit = (Button) view.findViewById(R.id.submit);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        comment = (EditText) view.findViewById(R.id.comment);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Review review = new Review();
                review.setComment(comment.getText().toString());
                review.setRating(String.valueOf(ratingBar.getRating()));
                review.setUserId(userId);
                review.setRdate(new SimpleDateFormat().format(Calendar.getInstance().getTime()));
                review.setStoreId(storeId);
                new AddReviewRequest(review, applicationContext).execute();
            }
        });
        return view;
    }


    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
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
