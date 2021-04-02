package com.example.shoppinglist.interfaces;

import com.example.shoppinglist.DatabaseListAccess;

/**
 * This is needed to be able to read the data from Firebase, specifically the accessKeys for all
 * of the shopping lists.
 *
 * The callback will allow access to the data outside of the override method.
 *
 * @author Team-06
 * @version 2021.03.31
 * @since 1.0
 */
public interface FirebaseCallbackListKeys {
    public void onCallback(DatabaseListAccess accessKeys);
}
