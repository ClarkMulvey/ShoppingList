package com.example.shoppinglist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseListAccess {

    // Map containing Key/Value for Default Lists
    private ArrayList<CustomMap> defaultListKeys;
    // Map containing Key/Value for Upcoming Lists
    private ArrayList<CustomMap> upcomingListKeys;
    private DataHandler data;
    private static String mainKey = "databaseAccessKeys";

    ArrayList<Map<Integer, String>> accessKeys;

    public DatabaseListAccess() {
        // instantiate data handler object
        this.data = new DataHandler();

        //Read the data from Firebase
        data.getListKeys(databaseListAccessObj -> {
            if (databaseListAccessObj != null) {
                if (databaseListAccessObj.getDefaultListKeys() != null) {
                    this.defaultListKeys = databaseListAccessObj.getDefaultListKeys();
                }
                if (databaseListAccessObj.getUpcomingListKeys() != null) {
                    this.upcomingListKeys = databaseListAccessObj.getUpcomingListKeys();
                }
            }

            if (this.defaultListKeys == null) {
                this.defaultListKeys = new ArrayList<>();
                this.defaultListKeys.add(new CustomMap("1","defaultList"));
                this.data.writeListKeys(this, mainKey);
            };
            if (this.upcomingListKeys == null) {
                //this.upcomingListKeys = new HashMap<>();
                //this.upcomingListKeys.put("Clark", "upcomingList");
                //this.data.writeListKeys(this, mainKey);
            }

        }, this.mainKey);







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
