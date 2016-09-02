package gr.uoa.ec.shopeeng.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.models.ShoppingItem;

import java.util.List;

public class ShoppingItemAdapter extends ArrayAdapter<ShoppingItem> {
    private static class ShoppingItemViewHolder {
        TextView storeName;
        TextView productName;
        TextView price;
    }

    public ShoppingItemAdapter(Context context, @NonNull List<ShoppingItem> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShoppingItemAdapter.ShoppingItemViewHolder shoppingItemViewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.list_item_shopping_list, parent, false);

            shoppingItemViewHolder = new ShoppingItemAdapter.ShoppingItemViewHolder();
            shoppingItemViewHolder.storeName = (TextView) convertView.findViewById(R.id.shopping_list_store_name);
            shoppingItemViewHolder.productName = (TextView) convertView.findViewById(R.id.shopping_list_product_name);
//            shoppingItemViewHolder.price = (TextView) convertView.findViewById(R.id.store_rating);

            convertView.setTag(shoppingItemViewHolder);
        } else {
            shoppingItemViewHolder = (ShoppingItemAdapter.ShoppingItemViewHolder) convertView.getTag();
        }

        ShoppingItem shoppingItem = getItem(position);

        if (shoppingItem != null) {
            if (shoppingItem.getStore() != null)
                shoppingItemViewHolder.storeName.setText(shoppingItem.getStore().getName());
            else
                shoppingItemViewHolder.storeName.setText("");

            shoppingItemViewHolder.productName.setText(shoppingItem.getProduct().getName());
//            shoppingItemViewHolder.price.setText("@@@@");//store.getName());
        }

        return convertView;
    }
}
