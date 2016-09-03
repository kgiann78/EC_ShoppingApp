package gr.uoa.ec.shopeeng.models;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingList {
    private Map<String, Product> productsMap = new LinkedHashMap<>();
    private Map<String, Store> shoppingMap = new LinkedHashMap<>();

    public Map<String, Store> getShoppingMap() {
        return shoppingMap;
    }

    public void setShoppingMap(Map shoppingMap) {
        this.shoppingMap = shoppingMap;
    }

    public Map<String, Product> getProductsMap() {
        return productsMap;
    }

    public void setProductsMap(Map<String, Product> productsMap) {
        this.productsMap = productsMap;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                ", shoppingMap=" + shoppingMap +
                ", productsMap=" + productsMap +
                '}';
    }
}
