package gr.uoa.ec.shopeeng;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductsFragment extends Fragment {

    public ProductsFragment() {
    }

    private TextView textView;
    String message = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("FragmentLifecycle", "onCreateView");

        View view = inflater.inflate(R.layout.fragment_products, container, false);
        textView = (TextView) view.findViewById(R.id.alekos_textView);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            if (textView != null) {
                textView.setText(args.getString("SEARCH_TEXT"));
            }
        }
    }
}
