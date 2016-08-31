package gr.uoa.ec.shopeeng.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.models.Store;

import java.util.List;

public class StoreAdapter extends ArrayAdapter<Store> {

    private static class StoreViewHolder {
        TextView storeName;
        TextView storeRating;
    }

    public StoreAdapter(Context context, @NonNull List<Store> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoreViewHolder storeViewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.list_item_store, parent, false);

            storeViewHolder = new StoreViewHolder();
            storeViewHolder.storeName = (TextView) convertView.findViewById(R.id.store_name);
            storeViewHolder.storeRating = (TextView) convertView.findViewById(R.id.store_rating);

            convertView.setTag(storeViewHolder);
        } else {
            storeViewHolder = (StoreViewHolder) convertView.getTag();
        }

        Store store = getItem(position);

        if (store != null) {
            storeViewHolder.storeName.setText(store.getName());
            storeViewHolder.storeRating.setText(store.getRating());
        }

        return convertView;
    }
}
