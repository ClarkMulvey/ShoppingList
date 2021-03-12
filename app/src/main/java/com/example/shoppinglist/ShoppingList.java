package com.example.shoppinglist;

import java.io.Serializable;
import java.util.ArrayList;

public class ShoppingList implements Serializable {
    private ArrayList<ShoppingListItem> items;

    public ShoppingList() {
        this.items = new ArrayList<>();
    }

    public void setItems(ArrayList<ShoppingListItem> items) {
        this.items = items;
    }

    public ArrayList<ShoppingListItem> getItems() {
        return this.items;
    }

    public void addItem(ShoppingListItem item) {
        this.items.add(item);
    }

    public void deleteItem(ShoppingListItem item) {
        this.items.remove(item);
    }
}
