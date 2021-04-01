package com.example.shoppinglist;

import java.io.Serializable;
import java.util.ArrayList;

public class DatabaseListAccess implements Serializable {

    // Map containing Key/Value for Default Lists
    private ArrayList<CustomMap> defaultListKeys;
    // Map containing Key/Value for Upcoming Lists
    private ArrayList<CustomMap> upcomingListKeys;
    private DataHandler data;
    private String mainKey;

    //TODO: I don't think this is doing anything
    //ArrayList<Map<Integer, String>> accessKeys;


    public DatabaseListAccess() {
        this.mainKey = "databaseAccessKeys";
    }

    public String getMainKey() {
        return this.mainKey;
    }

    public ArrayList<CustomMap> getDefaultListKeys() {
        return defaultListKeys;
    }

    public void setDefaultListKeys(ArrayList<CustomMap> defaultListKeys) {
        this.defaultListKeys = defaultListKeys;
    }

    public ArrayList<CustomMap> getUpcomingListKeys() {
        return upcomingListKeys;
    }

    public void setUpcomingListKeys(ArrayList<CustomMap> upcomingListKeys) {
        this.upcomingListKeys = upcomingListKeys;
    }
}
