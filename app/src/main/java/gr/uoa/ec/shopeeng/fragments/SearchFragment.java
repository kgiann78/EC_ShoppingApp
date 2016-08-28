package gr.uoa.ec.shopeeng.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.listeners.OnSearchClickedListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {
    private OnSearchClickedListener mCallback;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("FragmentLifecycle", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Button searchButton = (Button) view.findViewById(R.id.button);
        final EditText editText = (EditText) view.findViewById(R.id.editText);

        if (searchButton != null && editText != null) {
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onSearchClicked(editText.getText().toString());
                }
            });
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnSearchClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSearchClickedListener");
        }
    }
}
