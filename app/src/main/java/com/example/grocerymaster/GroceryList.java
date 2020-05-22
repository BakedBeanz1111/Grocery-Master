package com.example.grocerymaster;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class GroceryList implements Serializable {
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
}

class dateComparator implements Comparator<GroceryList> {
    @Override
    public int compare(GroceryList g1, GroceryList g2) {
        return Long.compare(g2.getDate(), g1.getDate());
    }

}

class listSizeComparator implements Comparator<GroceryList> {
    @Override
    public int compare(GroceryList g1, GroceryList g2) {
        return Integer.compare(g2.getGroceries().size(), g1.getGroceries().size());
    }

}

class alphabeticalComparator implements Comparator<GroceryList> {
    @Override
    public int compare(GroceryList g1, GroceryList g2) {
        return g1.getTitle().compareTo(g2.getTitle());
    }
}

class checksSizeComparator implements Comparator<GroceryList> {
    @Override
    public int compare(GroceryList g1, GroceryList g2) {
        int g1count = 0;
        for (Map.Entry<String, Boolean> entry : g1.getGroceryChecks().entrySet()) {
            if (entry.getValue()) {
                g1count++;
            }
        }
        int g2count = 0;
        for (Map.Entry<String, Boolean> entry : g2.getGroceryChecks().entrySet()) {
            if (entry.getValue()) {
                g2count++;
            }
        }
        return Integer.compare(g2count, g1count);
    }

}


