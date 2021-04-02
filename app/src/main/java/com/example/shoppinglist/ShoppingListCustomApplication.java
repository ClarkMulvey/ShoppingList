package com.example.shoppinglist;
import android.app.Application;
import android.content.res.Configuration;

import com.google.firebase.database.FirebaseDatabase;

/**
 * This is a helper class to setup custom attributes for this application.
 *
 * In order to change the default behavior of the Firebase implementation, this was added to allow
 * for persistence in case the device doesn't have network connectivity.
 *
 * @author Team-06
 * @version 2021.03.31
 * @since 1.0
 */
public class ShoppingListCustomApplication extends Application{

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        // Allow for Firebase Persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}