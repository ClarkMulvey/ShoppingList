package com.example.shoppinglist;

import java.io.Serializable;

/**
 * Map to store the Shopping list Key and Name
 *
 * This class creates a Map for the Key/Name combination to be used with Default and Upcoming
 * shopping lists.
 *
 * @author Team-06
 * @version 2021.0317
 * @since 1.0
 */
public class CustomMap implements Serializable {

    private String key;

    private String value;

    public CustomMap() { }

    public CustomMap(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
