package com.example.shoppinglist;

import java.io.Serializable;

public class ShoppingListItem implements Serializable {
    private String name;
    private int quantity;

    public ShoppingListItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public ShoppingListItem() {}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
