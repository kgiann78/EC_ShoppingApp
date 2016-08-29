package gr.uoa.ec.shopeeng.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShoppingListFragment extends Fragment {

    public ShoppingListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("FragmentLifecycle", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        if (getArguments() != null) {
            Bundle args = getArguments();
            ListView shoppingListView = (ListView) view.findViewById(R.id.list);
            TextView shoppingListNameTextView = (TextView) view.findViewById(R.id.shopping_list_name);
            shoppingListNameTextView.setText(args.getString(Constants.SHOPPING_LIST_NAME));
            ArrayList products = args.getParcelableArrayList(Constants.PRODUCTS_IN_SHOPPING_LIST);
            shoppingListView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, products));
            shoppingListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }

        return view;
    }


}
