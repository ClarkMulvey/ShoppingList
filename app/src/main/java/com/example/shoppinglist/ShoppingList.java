package com.example.shoppinglist;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class defines the elements of a shopping list
 *
 * Definition of the elements of a shopping list which includes an array of ShoppingListItems
 *
 * @author Team-06
 * @version 2021.03.31
 * @since 1.0
 */
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
