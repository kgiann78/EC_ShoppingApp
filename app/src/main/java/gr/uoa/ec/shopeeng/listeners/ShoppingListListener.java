package gr.uoa.ec.shopeeng.listeners;

import gr.uoa.ec.shopeeng.models.Product;
import gr.uoa.ec.shopeeng.models.Store;

public interface ShoppingListListener {
    void onAddItemToShoppingListClicked(Product product, Store store);
    void onDeleteItemFromShoppingListClicked(Product product, Store store);
}
