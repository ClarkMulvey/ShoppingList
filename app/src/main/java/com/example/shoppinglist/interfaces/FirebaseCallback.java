package com.example.shoppinglist.interfaces;

import com.example.shoppinglist.ShoppingList;

/**
 * This is needed to be able to read the data from Firebase.
 *
 * The callback will allow access to the data outside of the override method.
 *
 * @author Team-06
 * @version 2021.03.31
 * @since 1.0
 */
public interface FirebaseCallback {
    public void onCallback(ShoppingList list);


}

