package com.example.grocerymaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


/*Blank activity for showing info about the app*/
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle("Grocery Master");

    }
}
