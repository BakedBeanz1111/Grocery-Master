package com.example.grocerymaster;

import android.content.Intent;

import org.junit.Test;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;



import static androidx.core.app.ActivityCompat.startActivityForResult;
import static com.example.grocerymaster.MainActivity.*;
import static com.example.grocerymaster.MainActivity.getMainList;
import static java.lang.System.out;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
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
    @Test
    public void testDeleteList(){
        /*
        testDeleteList first checks that clearing the main list results in a maintained size of 0.
         */

        int expected=0;
        getMainList().clear();
        int actual=getMainList().size();
        assertEquals(expected, actual);


        ArrayList<String> actual1=new ArrayList<String>();//empty set of groceries
        GroceryList firstList1=new GroceryList();

        firstList1.setGroceries(actual1);//adds empty set to grocery instance
        getMainList().add(firstList1);//adds grocery list to mainList

        assertEquals(1, getMainList().size());//ensures that size was increased as list added
        getMainList().remove(firstList1);//removes List1 from Main List
        assertEquals(0, getMainList().size());//ensures mainList size was decreased by 1.
        assertNotSame(getMainList().indexOf(0), firstList1);//ensures mainList index 0 is not equal to List1
    }
    @Test
    public void testAlphabeticalComparator(){
        /*
        The purpose of this test is to ensure that lists are sorted alphabetically.
        Both lists are compared and firstList2 should be the higher list alphabetically resulting in
        a returned value of 1 (greater)
         */
        GroceryList firstList2=new GroceryList();
        GroceryList secondList2=new GroceryList();

        firstList2.setTitle("B-LIST");
        secondList2.setTitle("A-LIST");
        getMainList().add(firstList2);
        getMainList().add(secondList2);
        int expected=1;
        alphabeticalComparator set=new alphabeticalComparator();
        int compare=set.compare(firstList2, secondList2);
        assertEquals(expected, compare);


    }

    @Test
    public void testChecksSizeComparator(){
        GroceryList firstList3=new GroceryList();
        GroceryList secondList3=new GroceryList();

        HashMap<String, Boolean> bigList=new HashMap<String, Boolean>(){{
            put("Banana", true);
            put("orange", true);
            put("muffins", true);
        }};
        HashMap<String, Boolean> smallList=new HashMap<String, Boolean>(){{
            put("banana", true);
        }};

        firstList3.setGroceryChecks(smallList);
        secondList3.setGroceryChecks(bigList);
        getMainList().add(firstList3);
        getMainList().add(secondList3);

        checksSizeComparator set1= new checksSizeComparator();
        int compare=set1.compare(firstList3, secondList3); //firstList2 < secondList2
        int expected=1;
        assertEquals(expected, compare);


    }
    @Test
    public void testListSizeComparator(){
        GroceryList firstList3=new GroceryList();
        GroceryList secondList3=new GroceryList();

        ArrayList<String> bigList=new ArrayList<String>(){{
            add("banana");
            add("orange");
            add("muffins");
        }};
        ArrayList<String> smallList=new ArrayList<String>(){{
            add("banana");

        }};

        firstList3.setGroceries(smallList);
        secondList3.setGroceries(bigList);
        getMainList().add(firstList3);
        getMainList().add(secondList3);

        listSizeComparator set1= new listSizeComparator();
        int compare=set1.compare(firstList3, secondList3); //firstList2 < secondList2
        int expected=1;
        assertEquals(expected, compare);


    }
       @Test
    public void testDateComparator(){
        /*
        Testing date comparison method of Groceries
        */
        GroceryList firstList4=new GroceryList();
        GroceryList secondList4=new GroceryList();

        firstList4.setDate(1590719197);
        secondList4.setDate((1590719230));

        dateComparator set3=new dateComparator();
        int compare=set3.compare(firstList4,secondList4);

        int expected=1;
        assertEquals(expected, compare);
    }

}
