package com.example.grocerymaster;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class mainListViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView groceryText;
    TextView dateText;

    mainListViewHolder(View view) {
        super(view);
        title = view.findViewById(R.id.titleText);
        groceryText = view.findViewById(R.id.groceryText);
        dateText = view.findViewById(R.id.dateText);
    }

}
