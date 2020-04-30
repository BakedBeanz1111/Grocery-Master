package com.example.grocerymaster;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private static ArrayList<GroceryList> mainList = new ArrayList<>(); //holds our list of grocery lists
    private RecyclerView recyclerView; //view for showing our main list of grocery lists
    private mainListAdapter listAdapter; //adapter for updating our main list when it changes
    private int position; //keeps track of which item in the main list we clicked on
    private GroceryList selection; //reference to the list we chose from our main list to send to edit activity
    private static final int ADD_NEW_LIST = 111;
    private Type listType = new TypeToken<ArrayList<String>>() { //used for specifying a JSON arraylist to write it
    }.getType();
    private Type listType2 = new TypeToken<Map<String, Boolean>>() {
    }.getType();
    private static final String TAG = "kevin";

    /*called when the application starts, here we setup our views and get JSON data*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainList.clear();
        readJSON(); /*populates list from saved JSON file*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle);
        listAdapter = new mainListAdapter(mainList, this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //setDummyData(); /*populates list with arbitrary data*/
        setTitle("Grocery Master (" + mainList.size() + ")");
    }
    /*creates the options menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public static ArrayList<GroceryList> getMainList() {
        return mainList;
    }
    /*use to read our JSON file in our onCreate method*/
    private void readJSON() {
        try {
            InputStream inputStream = openFileInput("mydata.txt");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                String jsonText = stringBuilder.toString();
                try {
                    JSONArray jsonArray = new JSONArray(jsonText);
                    Gson gson = new Gson();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        long date = jsonObject.getLong("date");
                        GroceryList g = new GroceryList();
                        g.setDate(date);
                        g.setTitle(jsonObject.getString("title"));
                        ArrayList<String> groceries = gson.fromJson(jsonObject.getString("groceries"), listType);
                        Map<String,Boolean> groceryMap = gson.fromJson(jsonObject.getString("groceryChecks"), listType2);
                        for(String gr: groceries) {
                            g.getGroceries().add(gr);
                        }
                        for (Map.Entry<String,Boolean> entry : groceryMap.entrySet())   {
                            g.getGroceryChecks().put(entry.getKey(),entry.getValue());
                        }
                        mainList.add(g);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            //Toast.makeText(this, "File Not Found:" + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            //Toast.makeText(this, "Cannot read file:" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    /*writes each lists' data to a json file to make persistent for later*/
    private void writeJSON() {
        JSONArray jsonArray = new JSONArray();
        for (GroceryList g : mainList) {
            try {
                JSONObject groceryJSON = new JSONObject();
                Gson gson = new Gson();
                groceryJSON.put("groceries", gson.toJson(g.getGroceries(), listType));
                groceryJSON.put("date", g.getDate());
                groceryJSON.put("groceryChecks", gson.toJson(g.getGroceryChecks(), listType2));
                groceryJSON.put("title", g.getTitle());
                jsonArray.put(groceryJSON);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String jsonText = jsonArray.toString();
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("mydata.txt", Context.MODE_PRIVATE));
                outputStreamWriter.write(jsonText);
                outputStreamWriter.close();
            } catch (IOException e) {
                Toast.makeText(this, "File Write Failed:" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
    /*use this method to pre fill lists*/
    private void setDummyData() {
        for(int i=1; i < 11; i++) {
            ArrayList<String> groceries = new ArrayList<>();
            HashMap<String,Boolean> checks = new HashMap<>();
            for(int k=1; k < 6; k++) {
                String groc = "grocery item # " + k;
                groceries.add(groc);
                double willCheck = Math.random();
                if(willCheck < 0.5)
                    checks.put(groc, true);
                else
                    checks.put(groc, false);
            }
            GroceryList grocery = new GroceryList();
            grocery.setDate(System.currentTimeMillis());
            grocery.setGroceries(groceries);
            grocery.setGroceryChecks(checks);
            grocery.setTitle("Grocery List " + i);
            mainList.add(grocery);
        }
        listAdapter.notifyDataSetChanged();

    }
    /*This will be called when selecting an options icon, sets up an intent based on the button clicked*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent intent2 = new Intent(this, AboutActivity.class);
                startActivity(intent2);
                break;
            case R.id.add:
                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra("newPosition", mainList.size() + 1);
                startActivityForResult(intent, ADD_NEW_LIST);
                break;
            case R.id.delete:
                if (!mainList.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Are you sure you want to delete all Lists?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mainList.clear();
                            listAdapter.notifyDataSetChanged();
                            setTitle("Grocery Master (" + mainList.size() + ")");
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    /*Click listener for each list, will send the list as an intent to our edit activity*/
    @Override
    public void onClick(View v) {
        position = recyclerView.getChildLayoutPosition(v);
        selection = mainList.get(position);
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("LIST1", selection);
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        makeText(this, "Selected list #: " + selection.getTitle(), Toast.LENGTH_SHORT).show();
        startActivityForResult(intent, ADD_NEW_LIST);
    }
    /*This is our way of deleting individual grocery lists, will remove the list from our main list and
    * tell the adapter to update the view*/
    @Override
    public boolean onLongClick(View v) {
        position = recyclerView.getChildLayoutPosition(v);
        selection = mainList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete List " + (position + 1) + "?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mainList.remove(position);
                listAdapter.notifyDataSetChanged();
                setTitle("Grocery Master (" + mainList.size() + ")");
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        setTitle("Grocery Master (" + mainList.size() + ")");
        return true;
    }
    /*This will be called after we return from the edit activity, we will gather the new edited
    * grocery list and put it into our main list after we remove the old one, the adapter is then called
    * and the list view changes*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD_NEW_LIST) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                GroceryList groc = (GroceryList) data.getSerializableExtra("LIST");
                deleteDups(groc);
                mainList.add(groc);
                Collections.sort(mainList, Collections.reverseOrder());
                listAdapter.notifyDataSetChanged();

            }
            setTitle("Grocery Master (" + mainList.size() + ")");
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    /*This method will check for duplicates before adding back into the list
    * prevents list from keeping old list objects*/
    private void deleteDups(GroceryList groc) {
        ArrayList<GroceryList> temp = new ArrayList<>(mainList);
        for(GroceryList g: temp) {
            if(g.getId().equals(groc.getId())) {
                mainList.remove(g);
            }
        }
    }

    /*This method is called whenever the application is about to close, or before the orientation
    * changes*/
    @Override
    protected void onPause() {
        super.onPause();
        writeJSON();
    }

}