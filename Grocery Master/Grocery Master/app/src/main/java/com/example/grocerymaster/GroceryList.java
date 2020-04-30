package com.example.grocerymaster;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class GroceryList implements Serializable, Comparable<GroceryList> {
    private ArrayList<String> groceries = new ArrayList<>(); //stores each line item of the grocery list
    private Map<String, Boolean> groceryChecks = new HashMap<>(); //stores booleans for each item to keep it checked after closing the app
    private long date; //date for each grocery list that updates when edited
    private String title; //holds title
    private UUID id = UUID.randomUUID(); //makes each list unique

    public ArrayList<String> getGroceries() {
        return groceries;
    }

    public Map<String, Boolean> getGroceryChecks() {
        return groceryChecks;
    }

    public void setGroceries(ArrayList<String> groceries) {
        this.groceries = groceries;
    }

    public void setGroceryChecks(Map<String, Boolean> groceryChecks) {
        this.groceryChecks = groceryChecks;
    }

    public long getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getId() {
        return id;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }
    /*used to sort each list by date*/
    @Override
    public int compareTo(GroceryList o) {
        if(this.getDate() > o.getDate())
            return 1;
        else if(this.getDate() < o.getDate())
            return -1;
        else
            return 0;
    }
}
