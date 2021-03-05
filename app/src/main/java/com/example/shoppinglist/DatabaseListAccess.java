package com.example.shoppinglist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseListAccess implements Serializable {

    // Map containing Key/Value for Default Lists
    private ArrayList<CustomMap> defaultListKeys;
    // Map containing Key/Value for Upcoming Lists
    private ArrayList<CustomMap> upcomingListKeys;
    private DataHandler data;
    private static String mainKey = "databaseAccessKeys";

    ArrayList<Map<Integer, String>> accessKeys;

    public static String getMainKey() {
        return mainKey;
    }

    public static void setMainKey(String mainKey) {
        DatabaseListAccess.mainKey = mainKey;
    }

    public DatabaseListAccess() {
        // instantiate data handler object
        this.data = new DataHandler();
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
