package com.dc.quotify.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.dc.quotify.Holder.QuoteHolder;
import com.dc.quotify.Model.QuoteModel;
import com.dc.quotify.R;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Quotes extends AppCompatActivity {
    TextView head;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<QuoteModel> options;
    FirebaseRecyclerAdapter<QuoteModel, QuoteHolder> adapter;
    DatabaseReference rootRef;
    String child,global_uid,global_max;
    FloatingActionButton fab;
    private AdView mAdView;
    ImageView back;
    SharedPreferences app_preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);
        initz();
        String Child_Node = getIntent().getStringExtra("Child");
        String Uid =getIntent().getStringExtra("uid");
        String max =getIntent().getStringExtra("max");
        global_uid=Uid;
        global_max=max;
        child =Child_Node;
        head.setText(Child_Node);
        recyclerView = findViewById(R.id.quotes_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(Quotes.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        LoadData();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(0);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Quotes.super.onBackPressed();
                Animatoo.animateSlideDown(Quotes.this);
            }
        });
    }

    private void initz() {
        head=findViewById(R.id.head_text);
        rootRef=FirebaseDatabase.getInstance().getReference();
        rootRef.keepSynced(true);
        back=findViewById(R.id.back);
        fab=findViewById(R.id.topbtn);
        InitzAds();
        LoadBanner();
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
    private void InitzAds() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }
    private void LoadData() {
        if (global_max==null)
        {
            global_max="10";
        }
        int startIndex = ThreadLocalRandom.current().nextInt(1, Integer.parseInt(global_max));
        Query query  =rootRef.child("Category").child(child) .orderByChild("id").startAt(startIndex).endAt(startIndex + 120);
        options=new FirebaseRecyclerOptions.Builder<QuoteModel>().setQuery(query,QuoteModel.class).build();
        adapter=new FirebaseRecyclerAdapter<QuoteModel, QuoteHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final QuoteHolder holder, final int i, @NonNull final QuoteModel QuoteModel) {
                holder.quote.setText("''"+QuoteModel.getQuote()+"''");
                Random rnd = new Random();
                int color = Color.argb(250, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                holder.sv.setBackgroundColor(color);
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(Quotes.this, Quote_event.class);
                        intent.putExtra("Child",child);
                        intent.putExtra("DataKey",getRef(i).getKey());
                        intent.putExtra("uid",global_uid);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return super.getItemCount();
            }

            @NonNull
            @Override
            public QuoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotes,parent,false);
                return new QuoteHolder(v);

            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        app_preferences = PreferenceManager
                .getDefaultSharedPreferences(Quotes.this);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putBoolean("isFirstTime", true);
        editor.commit();
    }
}
