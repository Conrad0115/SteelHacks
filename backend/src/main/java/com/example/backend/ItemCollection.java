package com.example.backend;

import java.util.ArrayList;
import java.util.Date;

public class ItemCollection {
    private String itemName;
    ;
    private ArrayList<Date> dates;

    public ItemCollection(String itemName, ArrayList<Date> dates) {
        this.itemName = itemName;
        this.dates = dates;
    }

    // Getters and setters
    public String getKey() {
        return itemName;
    }

    public void setKey(String key) {
        this.itemName = key;
    }

    public ArrayList<Date> getDates() {
        return dates;
    }

    public void setDates(ArrayList<Date> dates) {
        this.dates = dates;
    }
}
