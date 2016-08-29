package gr.uoa.ec.shopeeng.utils;

import gr.uoa.ec.shopeeng.models.Product;
import gr.uoa.ec.shopeeng.models.ShoppingList;
import gr.uoa.ec.shopeeng.models.Store;

public class ShoppingListManager {
    ShoppingList shoppingList;

    public void addProduct(Product product) {
        if (!shoppingList.getMyShoppingList().containsKey(product)) {
            shoppingList.getMyShoppingList().put(product, null);
        }
    }

    public void associateProductToStore(Product product, Store store) {
        if (!shoppingList.getMyShoppingList().containsKey(product)) {
            shoppingList.getMyShoppingList().put(product, store);
        }
    }

    public void removeProduct(Product product) {
        if (shoppingList.getMyShoppingList().containsKey(product)) {
            shoppingList.getMyShoppingList().remove(product);
        }
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }
}
