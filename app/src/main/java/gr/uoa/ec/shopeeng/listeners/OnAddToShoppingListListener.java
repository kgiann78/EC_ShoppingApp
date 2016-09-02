package gr.uoa.ec.shopeeng.listeners;

import gr.uoa.ec.shopeeng.models.Product;
import gr.uoa.ec.shopeeng.models.Store;

public interface OnAddToShoppingListListener {
    void onAddProductToShoppingClicked(Product product, Store store);
}
