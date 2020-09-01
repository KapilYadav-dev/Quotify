package com.dc.quotify.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dc.quotify.R;

public class favouriteHolder extends RecyclerView.ViewHolder {
    public TextView quote;
    public ImageView delete;
    public ScrollView sv;
    public favouriteHolder(@NonNull View itemView) {
        super(itemView);
        quote=itemView.findViewById(R.id.text_view);
        sv=itemView.findViewById(R.id.scrollView);
        delete=itemView.findViewById(R.id.delete);
    }
}
