package com.example.shoppinglist;

import java.util.ArrayList;

public class ShoppingListDefault extends ShoppingList {

    private String name;

    public ShoppingListDefault(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
