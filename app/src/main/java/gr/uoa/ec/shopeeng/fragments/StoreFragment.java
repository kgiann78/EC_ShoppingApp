package gr.uoa.ec.shopeeng.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.adapters.CommentAdapter;
import gr.uoa.ec.shopeeng.models.Comment;
import gr.uoa.ec.shopeeng.models.Rating;
import gr.uoa.ec.shopeeng.models.Store;
import gr.uoa.ec.shopeeng.utils.Constants;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class StoreFragment extends Fragment {

    private FragmentManager fragmentManager;
    private Context applicationContext;
    private Store store;

    TextView storeName;
    RatingBar ratingBar;
    TextView storeAddress;
    TextView ratingScoreText;
    ListView commentsList;

    ArrayList<Comment> comments = new ArrayList<>();
    ArrayList<Rating> ratings = new ArrayList<>();

    Double ratingScore = 0.00;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store, container, false);
        storeName = (TextView) view.findViewById(R.id.store_name_text_view);
        ratingBar = (RatingBar) view.findViewById(R.id.store_rating_bar);
        storeAddress = (TextView) view.findViewById(R.id.store_address_text_view);
        ratingScoreText = (TextView) view.findViewById(R.id.rating_score_text_view);
        commentsList = (ListView) view.findViewById(R.id.comments_list);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getData();

        ratingScoreText.setText(getContext().getString(R.string.rating_score_value, ratingScore.toString(), ratings.size()));
        getListViews();
    }


    private void getData() {
        final Bundle args = getArguments();

        if (args != null) {
            comments = args.getParcelableArrayList(Constants.COMMENTS_RESULTS);
            Log.i("comments results", Arrays.toString(comments.toArray()));

            ratings = args.getParcelableArrayList(Constants.RATING_RESULTS);
            Log.i("ratings results", Arrays.toString(ratings.toArray()));

            ratingScore = args.getDouble(Constants.RATING_SCORE);
            store = args.getParcelable(Constants.STORE_RESULT);

            storeName.setText(store.getName());
            storeAddress.setText(store.getAddress());
            ratingBar.setRating(ratingScore.floatValue());
        }
    }

    private void getListViews() {
        commentsList.setAdapter(new CommentAdapter(getContext(), comments));
        if (getFragmentManager().findFragmentById(R.id.fragment_container) != null) {
            commentsList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

}
