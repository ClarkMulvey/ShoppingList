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
        //TODO: Need to write this method to read from the local Disk

        //TODO: Need to add to this method to also read from the cloud, although we need to determin
        // when the right time is to read from the cloud.

    }

    public void writeData(){

        //TODO: Need to write this method to write to the local Disk

        //TODO: Need to add to this method to also write to the cloud, although we need to determine
        // when the right time is to write to the cloud.
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
