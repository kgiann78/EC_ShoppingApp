package gr.uoa.ec.shopeeng;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {
    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("FragmentLifecycle", "onCreateView");

        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}
