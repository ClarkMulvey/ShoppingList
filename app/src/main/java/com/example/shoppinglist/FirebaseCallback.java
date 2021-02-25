package com.example.shoppinglist;

/*
This is needed to be able to red the data from Firebase, more specifically in order to be able
to use the data ourside of the override method.
 */
public interface FirebaseCallback {
    public void onCallback(ShoppingListDefault list);
}

