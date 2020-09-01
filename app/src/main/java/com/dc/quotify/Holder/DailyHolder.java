package com.dc.quotify.Holder;

import android.graphics.Color;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.dc.quotify.R;

import java.util.Random;

public class DailyHolder extends RecyclerView.ViewHolder {
    public TextView textView,cat;
    public ScrollView rl;
    public  View v;
    public DailyHolder(@NonNull View itemView) {
        super(itemView);
        textView=itemView.findViewById(R.id.text_view);
        rl=itemView.findViewById(R.id.relative_layout);
        v= itemView;
        cat=itemView.findViewById(R.id.cat);
    }
}
