package gr.uoa.ec.shopeeng.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.adapters.ShoppingItemAdapter;
import gr.uoa.ec.shopeeng.models.ShoppingItem;
import gr.uoa.ec.shopeeng.utils.Constants;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShoppingListFragment extends Fragment {

    public ShoppingListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        if (getArguments() != null) {
            Bundle args = getArguments();
            ListView shoppingListView = (ListView) view.findViewById(R.id.shopping_list);
            ArrayList<ShoppingItem> shoppingItems = args.getParcelableArrayList(Constants.ITEMS_IN_SHOPPING_LIST);

            shoppingListView.setAdapter(new ShoppingItemAdapter(getActivity(), shoppingItems));
            shoppingListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }

        return view;
    }


}
