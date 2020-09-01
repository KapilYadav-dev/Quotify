package com.dc.quotify.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.dc.quotify.BuildConfig;
import com.dc.quotify.R;

public class More extends AppCompatActivity {
    TextView head,privacy,fb,share,contact,rate;
    ImageView back;
    SharedPreferences app_preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        initz();
        Typeface typeface = Typeface.createFromAsset(More.this.getAssets(), "rund.ttf");
        head.setTypeface(typeface);
        contact=findViewById(R.id.contact);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + More.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" +  More.this.getPackageName())));
                }
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","infoquotify@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Enter your Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Enter you Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
        share=findViewById(R.id.more);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check Best Quote's app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(More.this, MainActivity.class);
                i.putExtra("app_preferences",false);
                startActivityForResult(i, 1);
                Animatoo.animateSlideDown(More.this);
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://m.uploadedit.com/beth/1586626948419.txt");
                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                intentBuilder.setToolbarColor(ContextCompat.getColor(More.this, R.color.nav));
                intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(More.this, R.color.colorPrimaryDark));
                CustomTabsIntent customTabsIntent = intentBuilder.build();
                customTabsIntent.launchUrl(More.this, uri);
              //
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://fb.me/quotify99");
                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                intentBuilder.setToolbarColor(ContextCompat.getColor(More.this, R.color.fb));
                intentBuilder.setShowTitle(true);
                CustomTabsIntent customTabsIntent = intentBuilder.build();
                customTabsIntent.launchUrl(More.this, uri);
            }
        });
    }
    private void initz() {
        head=findViewById(R.id.head_text);
        rate=findViewById(R.id.rate_us);
        back=findViewById(R.id.back);
        privacy=findViewById(R.id.privacy);
        fb=findViewById(R.id.fb);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(More.this, MainActivity.class);
        i.putExtra("app_preferences",false);
        startActivityForResult(i, 1);
        Animatoo.animateSlideDown(More.this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        app_preferences = PreferenceManager
                .getDefaultSharedPreferences(More.this);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putBoolean("isFirstTime", true);
        editor.commit();
    }
}
