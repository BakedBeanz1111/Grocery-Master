package com.example.grocerymaster;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class mainListAdapter extends RecyclerView.Adapter<mainListViewHolder> {

    private ArrayList<GroceryList> list; //reference to our mainList in main activity
    private MainActivity mainActivity;
    private final String TAG = "insideAdapter";

    mainListAdapter(ArrayList<GroceryList> list, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.list = list;
    }

    /*gets references to our individual views in each adapter view and sets click listeners*/
    @NonNull
    @Override
    public mainListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_item, parent, false);
        itemView.setOnClickListener(mainActivity);
        itemView.setOnLongClickListener(mainActivity);
        return new mainListViewHolder(itemView);
    }

    /*this is called when we update our main List, will update each of the views for each list in main List */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull mainListViewHolder holder, int position) {
        GroceryList selection = list.get(position);
        Map<String, Boolean> ourMap = selection.getGroceryChecks();
        ArrayList<String> list = selection.getGroceries();
        holder.title.setText(selection.getTitle());
        Date date = new Date(selection.getDate());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EE MMM dd, hh:mm a");
        holder.dateText.setText(simpleDateFormat1.format(date));
        StringBuilder groc = new StringBuilder();
        for (String s : list) {
            if (!ourMap.isEmpty() && ourMap.containsKey(s)) {
                if (ourMap.get(s) != null) {
                    if (ourMap.get(s)) {
                        groc.append(s).append("   ✔\n");
                    }
                    else
                        groc.append(s).append("\n");
                }
            }

        }
        holder.groceryText.setText(groc.toString());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
