package com.example.shoppinglist;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This Class is to handle reading and writing the list keys for the instances in Firebase
 *
 * This class will manage the keys for the default lists and the upcoming lists in order to be keep
 * track of the lists in order to get the correct one.
 *
 * @author Team-06
 * @version 2021.03.31
 * @since 1.0
 */
public class DatabaseListAccess implements Serializable {

    // Map containing Key/Value for Default Lists
    private ArrayList<CustomMap> defaultListKeys;
    // Map containing Key/Value for Upcoming Lists
    private ArrayList<CustomMap> upcomingListKeys;
    private DataHandler data;
    private String mainKey;

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
