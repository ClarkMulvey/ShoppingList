package com.example.shoppinglist;

import java.util.ArrayList;

public class UpcomingShoppingLists {
    ArrayList<String> listNames ;

    public UpcomingShoppingLists() {
        DataHandler data = new DataHandler();
    }

    public ArrayList<String> getListNames() {
        return listNames;
    }

    public void setListNames(ArrayList<String> listNames) {
        this.listNames = listNames;
    }
}
