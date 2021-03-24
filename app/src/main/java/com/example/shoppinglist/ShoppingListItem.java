package com.example.shoppinglist;

import java.io.Serializable;

public class ShoppingListItem implements Serializable {
    private String name;
    private int quantity;
    private boolean completed;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public ShoppingListItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.completed = false;
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
