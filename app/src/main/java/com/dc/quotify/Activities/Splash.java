package com.dc.quotify.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dc.quotify.R;

public class Splash extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Typeface typeface = Typeface.createFromAsset(Splash.this.getAssets(), "best.ttf");
        textView = findViewById(R.id.text);
        textView.setTypeface(typeface);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, MainActivity.class);
                i.putExtra("app_preferences",true);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}