package com.example.grocerymaster;

import android.content.Intent;

import org.junit.Test;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;



import static androidx.core.app.ActivityCompat.startActivityForResult;
import static com.example.grocerymaster.MainActivity.*;
import static com.example.grocerymaster.MainActivity.getMainList;
import static java.lang.System.out;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import org.junit.Test;
import org.junit.runner.RunWith;


public class MainActivityTest {

    @Test
    public void testAddList() {
    /*This first test asserts that adding an empty list works and remains empty.
    List is added to MainList (initially empty) and the first index of MainList is compared
    to test List(g).
     */
        ArrayList<String> expected=new ArrayList<String>();
        ArrayList<String> actual=new ArrayList<String>();//empty set of groceries
        GroceryList firstList=new GroceryList();
        firstList.setGroceries(actual);//adds empty set to grocery instance
        getMainList().add(firstList);//adds grocery list to mainList
        assertEquals(expected, firstList.getGroceries());//asserts that grocery list is empty
        assertNotNull(getMainList());//checks that main list is not empty.
        assertEquals(firstList, getMainList().get(0));//checks that mainList's first index is equal to test List
    /*
    With preconditions being set above, mainList is current not empty. Now testing adding a list with lists
    existing.
     */
        GroceryList secondList=new GroceryList();//create instance of second list
        getMainList().add(secondList);//add second list to mainList
        assertEquals(secondList, getMainList().get(1));//check that secondList matches second index of mainList

    }
    @Test
    public void testAddToList(){
        /*Preconditions: 1. List is initially empty and items are added.
        2. List is non-empty and items are added.
         */
        ArrayList<String> expected=new ArrayList<String>(){{
            add("banana");
            add("orange");
            add("muffins");
        }};
        ArrayList<String> actual=new ArrayList<String>(){{
            add("banana");
            add("orange");
            add("muffins");
        }};

        GroceryList g=new GroceryList();
        g.setGroceries(actual);
        assertEquals(expected,g.getGroceries());

        //Now with preconditions met for second part of test, adding more items to list
        expected.add("pear");
        expected.add("peach");
        actual.add("pear");
        actual.add("peach");
        assertEquals(expected,g.getGroceries());


    }


}