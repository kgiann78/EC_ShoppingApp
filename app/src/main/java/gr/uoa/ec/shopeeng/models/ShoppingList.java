package gr.uoa.ec.shopeeng.models;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingList {
    private String name;
    private Date creationDate;
    private Map myShoppingList = new LinkedHashMap();

    public Map getMyShoppingList() {
        return myShoppingList;
    }

    public void setMyShoppingList(Map myShoppingList) {
        this.myShoppingList = myShoppingList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
