package gr.uoa.ec.shopeeng.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.*;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.models.Rating;
import gr.uoa.ec.shopeeng.models.Store;
import gr.uoa.ec.shopeeng.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */

public class StoreFragment extends ListFragment {

    private FragmentManager fragmentManager;
    private Context applicationContext;
    private Store store;

    ArrayList comments = new ArrayList();
    ArrayList ratings = new ArrayList();

    Double ratingScore = 0.00;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
        }
        Log.i("FragmentLifecycle", "onCreateView");
    }

    @Override
    public void onStart() {
        super.onStart();

        getData();
        // getStoreInfoView();
        getRatingScoreViews();
        getListViews();
    }


    private void getData() {
        final Bundle args = getArguments();
        comments = args.getParcelableArrayList(Constants.COMMENTS_RESULTS);
        Log.i("comments results", Arrays.toString(comments.toArray()));

        ratings = args.getParcelableArrayList(Constants.RATING_RESULTS);
        Log.i("ratings results", Arrays.toString(ratings.toArray()));

        ratingScore = args.getDouble(Constants.RATING_SCORE);
        store = args.getParcelable(Constants.STORE_RESULT);
    }


    private void getRatingScoreViews() {

        TextView ratingView = new TextView(applicationContext);
        ratingView.setText(ratingScore.toString());

        ViewGroup.LayoutParams layoutParams = new ViewPager.LayoutParams();
        getFragmentManager().findFragmentById(R.id.fragment_container).getActivity()
                .addContentView(ratingView, layoutParams);
    }

    //TODO fix this - add store info correctly

    private void getStoreInfoView() {

        TextView ratingView = new TextView(applicationContext);
        ratingView.setText(store.toString());
        ViewGroup.LayoutParams layoutParams = new ViewPager.LayoutParams();
        getFragmentManager().findFragmentById(R.id.fragment_container).getActivity()
                .addContentView(ratingView, layoutParams);
    }

    private void getListViews() {
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, comments));
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ratings));
        if (getFragmentManager().findFragmentById(R.id.fragment_container) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

}
