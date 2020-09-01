package com.dc.quotify.Holder;


import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dc.quotify.R;



public class QuoteHolder  extends RecyclerView.ViewHolder {
    public TextView quote;
    public View v;
    public ScrollView sv;
    public QuoteHolder(@NonNull View itemView) {
        super(itemView);
        quote=itemView.findViewById(R.id.text_view);
        sv=itemView.findViewById(R.id.scrollView);
        v=itemView;
    }
}
