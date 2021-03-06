package com.example.shoppinglist;

import java.io.Serializable;

/**
 * This class defines a ShoppingListItem
 *
 * Definition of a ShoppingListItem which is made up of a name, quantity and whether the item has
 * been completed
 *
 * @author Team-06
 * @version 2021.03.31
 * @since 1.0
 */

public class ShoppingListItem implements Serializable {
    private String name;
    private Integer quantity;
    private boolean completed;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public ShoppingListItem(String name, Integer quantity) {
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

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return this.quantity;
    }
}
