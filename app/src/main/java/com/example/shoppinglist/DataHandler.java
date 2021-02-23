package com.example.shoppinglist;



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

    }
}
