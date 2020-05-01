package com.example.grocerymaster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.nio.DoubleBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EditActivity extends AppCompatActivity {
    private TextView title; //reference to the title view
    private TextView dateText; //reference to the date view
    private static ArrayList<String> groceries; //holds our current set of grocery items
    private static ArrayList<EditText> groceryViews; //holds our grocery views to fill them and extract data from
    private static ArrayList<CheckBox> checkboxes; //holds checkbox views to check or uncheck them based on the individual list data
    private static Map<String, Boolean> checks; //holds the map of grocery item and checkbox booleans we are editing
    private static GroceryList g; //reference to the list that was passed from main activity
    private static final String TAG = "EditTag";

    /*This is called when we come from main activity, we will save the data we got to instance variables or create new lists if we
     * did not recieve data, ie creating a new list*/
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Grocery Master");
        setupViews(); //creates lists of views for checkboxes and line items
        Intent intent = getIntent();
        if (intent.hasExtra("LIST1")) {
            g = (GroceryList) intent.getSerializableExtra("LIST1");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EE MMM dd, hh:mm a");
            dateText.setText("Last updated: " + simpleDateFormat1.format(g.getDate()));
            groceries = new ArrayList<String>(g.getGroceries());
            title.setText(g.getTitle());
            checks = new HashMap<>(g.getGroceryChecks());
            setGroceries();
        } else {
            groceries = new ArrayList<>();
            checks = new HashMap<>();
            dateText.setVisibility(View.INVISIBLE);
            title.setText("Grocery List " + (MainActivity.getMainList().size() + 1));
            g = new GroceryList();
            g.setTitle("Grocery List " + (MainActivity.getMainList().size() + 1));
        }
    }

    /*Overrides the normal back press to include a dialogue warning the user that data was not saved*/
    @Override
    public void onBackPressed() {
        addViewData();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final TextView tv = new TextView(this);
        builder.setView(tv);
        builder.setTitle("Your list is not saved! Save list?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent data = new Intent();
                g.getGroceries().clear();
                for (String gr : groceries) {
                    g.getGroceries().add(gr);
                }
                g.getGroceryChecks().clear();
                for (Map.Entry<String, Boolean> entry : checks.entrySet()) {
                    g.getGroceryChecks().put(entry.getKey(), entry.getValue());
                }
                g.setDate(System.currentTimeMillis());
                data.putExtra("LIST", g);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    /*this method gets the strings from our grocery list and adds each string to an individual, editable view*/
    private static void setGroceries() {
        for (int i = 0; i < groceries.size(); i++) {
            groceryViews.get(i).setText(groceries.get(i));
            if ((checks.get(groceries.get(i)) != null)) {
                if (checks.get(groceries.get(i))) {
                    checkboxes.get(i).setChecked(true);
                }
            }
        }
    }

    /*sets the save button*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editmenu, menu);
        return true;
    }

    /*gets data from the views and adds them to the grocery list we are editing, then sets an intent and goes
     * back to the main activity*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent data = new Intent();
        addViewData();
        if (groceries.isEmpty()) {
            Toast.makeText(this, "The empty list was not saved", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            ArrayList<String> temp = new ArrayList<>(groceries);
            g.getGroceries().clear();
            for (String gro : temp) {
                g.getGroceries().add(gro);
            }
            g.getGroceryChecks().clear();
            for (Map.Entry<String, Boolean> entry : checks.entrySet()) {
                g.getGroceryChecks().put(entry.getKey(), entry.getValue());
            }
            g.setDate(System.currentTimeMillis());
            data.putExtra("LIST", g);
            setResult(RESULT_OK, data);
            MainActivity.getMainList().remove(g);
            finish();
        }
        groceries.clear();
        return true;
    }

    /*extracts data from each line and each checkbox in the activity and adds them to our lists*/
    private static void addViewData() {
        groceries.clear();
        for (EditText e : groceryViews) {
            if (!e.getText().toString().equals("") && e.getText().toString().length() > 0) {
                groceries.add(e.getText().toString());
                e.getText().clear();
            }
        }
        for (int i = 0; i < groceries.size(); i++) {
            if (checkboxes.get(i).isChecked()) {
                checks.put(groceries.get(i), true);
            } else {
                checks.put(groceries.get(i), false);
            }

        }
        checkboxes.clear();
    }

    /*gets references to each line and checkbox*/
    private void setupViews() {
        checkboxes = new ArrayList<>();
        groceryViews = new ArrayList<>();
        title = findViewById(R.id.title);
        dateText = findViewById(R.id.date);
        groceryViews.add(findViewById(R.id.lineItem1));
        groceryViews.add(findViewById(R.id.lineItem2));
        groceryViews.add(findViewById(R.id.lineItem3));
        groceryViews.add(findViewById(R.id.lineItem4));
        groceryViews.add(findViewById(R.id.lineItem5));
        groceryViews.add(findViewById(R.id.lineItem6));
        groceryViews.add(findViewById(R.id.lineItem7));
        groceryViews.add(findViewById(R.id.lineItem8));
        groceryViews.add(findViewById(R.id.lineItem9));
        groceryViews.add(findViewById(R.id.lineItem10));
        checkboxes.add(findViewById(R.id.checkBox1));
        checkboxes.add(findViewById(R.id.checkBox2));
        checkboxes.add(findViewById(R.id.checkBox3));
        checkboxes.add(findViewById(R.id.checkBox4));
        checkboxes.add(findViewById(R.id.checkBox5));
        checkboxes.add(findViewById(R.id.checkBox6));
        checkboxes.add(findViewById(R.id.checkBox7));
        checkboxes.add(findViewById(R.id.checkBox8));
        checkboxes.add(findViewById(R.id.checkBox9));
        checkboxes.add(findViewById(R.id.checkBox10));
    }
}