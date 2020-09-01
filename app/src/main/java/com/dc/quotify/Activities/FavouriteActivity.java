package com.dc.quotify.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.dc.quotify.Holder.favouriteHolder;
import com.dc.quotify.Model.favouriteModel;
import com.dc.quotify.R;
import com.google.firebase.database.ValueEventListener;
import com.tapadoo.alerter.Alerter;

import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FavouriteActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<favouriteModel> options;
    FirebaseRecyclerAdapter<favouriteModel, favouriteHolder> adapter;
    DatabaseReference rootRef;
    String uid,str_init;
    private InterstitialAd mInterstitialAd;
    ImageView back;
    private AdView mAdView;
    SharedPreferences app_preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        String Uid =getIntent().getStringExtra("uid");
        uid=Uid;
        ShowAlerter();
        InitzAds();
        InitzAds();
        back = findViewById(R.id.back);
        rootRef= FirebaseDatabase.getInstance().getReference();
        ReadInitzID();
        LoadBanner();
        recyclerView = findViewById(R.id.favourite_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        LoadData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FavouriteActivity.this, MainActivity.class);
                i.putExtra("app_preferences",false);
                startActivityForResult(i, 1);
                Animatoo.animateSlideDown(FavouriteActivity.this);
            }
        });
    }

    private void ShowAlerter() {
        Alerter.create(FavouriteActivity.this)
        .setTitle("Favorite")
                .setTitleAppearance(R.style.AlertTextAppearance_Title)
                .setText("Manage your favourite quote's from here..")
                .setTextAppearance(R.style.AlertTextAppearance_Text)
                .setBackgroundColorRes(R.color.fb)
                .setIcon(R.drawable.icon)
                .enableIconPulse(true)
                .enableSwipeToDismiss()
                .setTextTypeface(Typeface.createFromAsset(getAssets(), "rund.ttf"))
                .show();
    }

    private void LoadBanner() {
        rootRef.child("Admob").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String banner_alive =dataSnapshot.child("banner_alive").getValue(String.class);
                if (banner_alive==null)
                {
                    mAdView = findViewById(R.id.adView);
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);
                }
                else if (banner_alive.equalsIgnoreCase("on"))
                {
                    mAdView = findViewById(R.id.adView);
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);
                }
                else if (banner_alive.equalsIgnoreCase("off"))
                {

                }
                else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ReadInitzID() {
        rootRef.child("Admob").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String initz=dataSnapshot.child("inter").getValue(String.class);
                str_init=initz;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void InitzAds() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }
    private void LoadInitz() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(str_init);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
    }


    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
    private void LoadData() {
        options=new FirebaseRecyclerOptions.Builder<favouriteModel>().setQuery(rootRef.child("Users").child(uid),favouriteModel.class).build();
        adapter=new FirebaseRecyclerAdapter<favouriteModel, favouriteHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull favouriteHolder holder, final int i, @NonNull favouriteModel favouriteModel) {
                holder.quote.setText(favouriteModel.getQuote());
                Random rnd = new Random();
                int color = Color.argb(250, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                holder.sv.setBackgroundColor(color);
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LoadInitz();
                        final SweetAlertDialog pDialog = new SweetAlertDialog(FavouriteActivity.this, SweetAlertDialog.WARNING_TYPE);
                        pDialog.setTitleText("Delete")
                                .setContentText("Are you sure to delete message!")
                                .setCancelText("No")
                                .setConfirmText("Yes,delete it")
                                .showCancelButton(true)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("Users")
                                                .child(uid)
                                                .child(getRef(i).getKey())
                                                .setValue(null);
                                        pDialog.dismissWithAnimation();
                                    }
                                })
                                .show();
                    }
                });
            }

            @NonNull
            @Override
            public favouriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite,parent,false);
                return new favouriteHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(FavouriteActivity.this, MainActivity.class);
        i.putExtra("app_preferences",false);
        startActivityForResult(i, 1);
        Animatoo.animateSlideDown(FavouriteActivity.this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        app_preferences = PreferenceManager
                .getDefaultSharedPreferences(FavouriteActivity.this);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putBoolean("isFirstTime", true);
        editor.commit();
    }
}
