package gr.uoa.ec.shopeeng.utils;

import gr.uoa.ec.shopeeng.models.*;
import gr.uoa.ec.shopeeng.requests.DeleteItemListRequest;

import java.util.ArrayList;

public class ShoppingListManager {
    ShoppingList shoppingList = new ShoppingList();

    public boolean productAlreadyInList(Product product) {
        return shoppingList.getShoppingMap().containsKey(product.getProductId());
    }

    public void addProduct(Product product, Store store) {
        if (!shoppingList.getShoppingMap().containsKey(product.getProductId())) {
            shoppingList.getProductsMap().put(product.getProductId(), product);
        }
        shoppingList.getShoppingMap().put(product.getProductId(), store);
    }

    public void removeProduct(Product product) {
        if (shoppingList.getShoppingMap().containsKey(product.getProductId())) {
            shoppingList.getShoppingMap().remove(product.getProductId());
            shoppingList.getProductsMap().remove(product.getProductId());
        }

    }

    public ArrayList<ShoppingItem> getShoppingItems() {

        ArrayList<ShoppingItem> shoppingItems = new ArrayList<>();

        for (String productId : shoppingList.getShoppingMap().keySet()) {
            ShoppingItem item = new ShoppingItem(shoppingList.getProductsMap().get(productId),
                    shoppingList.getShoppingMap().get(productId));
            shoppingItems.add(item);
        }
        return shoppingItems;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public void initList(ArrayList<ListItem> listItems) {

        for (ListItem item : listItems) {
            Store s = new Store(item.getStoreId(), item.getStoreName(), item.getStoreAddress(), item.getStoreCountry(), " ", " ", item.getStorePrice(), "0", "0");
            Product p = new Product(item.getProductId(), item.getProductName(), item.getProductCategory(), item.getProductDescription(), item.getStorePrice());
            shoppingList.getProductsMap().put(p.getProductId(), p);
            shoppingList.getShoppingMap().put(p.getProductId(), s);
        }
    }
}
