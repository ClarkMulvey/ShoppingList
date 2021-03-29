package com.example.shoppinglist.interfaces;

import com.example.shoppinglist.ShoppingList;

/*
This is needed to be able to red the data from Firebase, more specifically in order to be able
to use the data outside of the override method.
 */
public interface FirebaseCallback {
    public void onCallback(ShoppingList list);


}

