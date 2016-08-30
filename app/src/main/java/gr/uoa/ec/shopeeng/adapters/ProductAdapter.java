package gr.uoa.ec.shopeeng.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.models.Product;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private static class ProductViewHolder {
        TextView productName;
        TextView productPrice;
    }

    public ProductAdapter(Context context, List<Product> resource) {
        super(context, R.layout.list_item_product, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductViewHolder productViewHolder;

        if(convertView==null) {
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
}
