package com.example.shoppinglist;

public class CustomMap {

    private String key;

    private String value;

    public CustomMap(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public CustomMap() { }

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
