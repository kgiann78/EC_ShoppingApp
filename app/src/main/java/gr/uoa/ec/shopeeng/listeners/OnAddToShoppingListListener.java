package gr.uoa.ec.shopeeng.listeners;

import gr.uoa.ec.shopeeng.models.Product;

public interface OnAddToShoppingListListener {
    void onAddProductToShoppingClicked(Product product);
}
