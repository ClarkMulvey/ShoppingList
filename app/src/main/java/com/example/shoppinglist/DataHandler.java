package com.example.shoppinglist;


import android.content.SharedPreferences;

import com.google.gson.Gson;

public class DataHandler {
     DefaultShoppingLists defaultLists;
     UpcomingShoppingLists upcomingLists;
     //String filename;    //Not sure if we need this one now

    public DefaultShoppingLists getDefaultLists() {
        return defaultLists;
    }

    public void setDefaultLists(DefaultShoppingLists defaultLists) {
        this.defaultLists = defaultLists;
    }

    public UpcomingShoppingLists getUpcomingLists() {
        return upcomingLists;
    }

    public void setUpcomingLists(UpcomingShoppingLists upcomingLists) {
        this.upcomingLists = upcomingLists;
    }

    public void readData(){

    }

    public void writeData(){
       /* Gson gson = new Gson();

        String listStorage = gson.toJson(this.defaultList);

        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.shoppinglist", MODE_PRIVATE);

        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString(defaultList.getName(), listStorage);

        myEdit.apply();

        */
    }
}
