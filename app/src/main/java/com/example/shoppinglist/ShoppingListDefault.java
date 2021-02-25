package com.example.shoppinglist;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class ShoppingListDefault extends ShoppingList {

    private String name;

    public ShoppingListDefault() {}

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
