package com.example.grocerymaster;

import android.content.Intent;

import org.junit.Test;
import android.view.MenuItem;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


import static androidx.core.app.ActivityCompat.startActivityForResult;
import static com.example.grocerymaster.MainActivity.*;
import static com.example.grocerymaster.MainActivity.getMainList;
import static java.lang.System.out;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MainActivityTest {

    @Test
    public void testAddEmptyList() {
    //adding list to empty mainList
        int expected= 1;
        int actual;



    }
    @Test
    public void testAddList(){
        //add list to non-empty mainList
        int expected=getMainList().size()+1;
        int actual;
    }
    @Test
    public void deleteListEmptyList(){
        //delete list from empty mainList
        int expected=0;

    }

    @Test
    public void deleteList(){
        //delete list from non-empty mainList
        int expected=getMainList().size()-1;
    }

}