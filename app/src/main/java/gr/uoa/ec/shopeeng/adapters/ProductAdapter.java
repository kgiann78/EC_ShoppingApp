package gr.uoa.ec.shopeeng.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends ArrayAdapter<Product> {

    private static class ProductViewHolder {
        TextView productName;
        TextView productPrice;
    }

    List<Product> resource;
    List<Product> originalResource;
    int charSeqCount = 0;

    public ProductAdapter(Context context, List<Product> resource) {
        super(context, R.layout.list_item_product, resource);
        this.resource = resource;
        this.originalResource = new ArrayList<>(resource);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ProductViewHolder productViewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.list_item_product, parent, false);

            productViewHolder = new ProductViewHolder();
            productViewHolder.productName = (TextView) convertView.findViewById(R.id.product_name);
            productViewHolder.productPrice = (TextView) convertView.findViewById(R.id.product_price);

            convertView.setTag(productViewHolder);
        } else {
            productViewHolder = (ProductViewHolder) convertView.getTag();
        }

        Product product = getItem(position);

        if (product != null) {
            productViewHolder.productName.setText(product.getName());
            productViewHolder.productPrice.setText(getContext().getString(R.string.product_price, product.getPrice()));
        }

        return convertView;
    }

    void removeAll() {
        List<Product> itemsToRemove = new ArrayList<>();
        for (int i = 0 ; i < getCount(); i++) {
            itemsToRemove.add(getItem(i));
        }

        for (Product product : itemsToRemove) {
            remove(product);
        }
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                List<Product> filteredResource = new ArrayList<>();

                if (charSequence == null || charSequence.length() == 0 || charSequence.length() < charSeqCount) {

                    // set the Original result to return
                    results.count = originalResource.size();
                    results.values = originalResource;
                    //charSeqCount is capturing the deletion of a character
                    charSeqCount = charSequence.length();

                } else {
                    Locale locale = Locale.getDefault();
                    charSeqCount = charSequence.length();
                    charSequence = charSequence.toString().toLowerCase(locale);

                    for (int i = 0; i < getCount(); i++) {
                        if (getItem(i).getName().toLowerCase().contains(charSequence.toString())) {
                            filteredResource.add(getItem(i));
                        }
                    }

                    results.count = filteredResource.size();
                    results.values = filteredResource;
                }

                return results;
            }



            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults.count > 0) {
                    // remove every product from list
                    removeAll();
                    // add results from filtering to show
                    addAll((List<Product>) filterResults.values);
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
}
