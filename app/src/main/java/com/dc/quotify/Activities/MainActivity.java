package com.dc.quotify.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.dc.quotify.Holder.DailyHolder;
import com.dc.quotify.Model.DailyModel;
import com.dc.quotify.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {
    TextView head;
    ImageView close;
    SmoothBottomBar bottomBar;
    CardView Anniversary, Attitude, Beautiful, Birthday, Children, College, Hindi, Emotional, Family, Food, Funy, Good_Morning, Good_Night, Gym, Inspirational, Love, Motivational, Romantic, Single, Sorry, Work, Break_up, Brother, Creative, Entrepreneur, Exam, Fathers_Day, Marriage, Mothers_Day, Music, Sister, Smart, Trust, Whatsapp;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<DailyModel> options;
    FirebaseRecyclerAdapter<DailyModel, DailyHolder> adapter;
    DatabaseReference rootRef;
    String anni_max, atti_max, beau_max, birt_max, chil_max, hind_max, coll_max, emot_max, fami_max, food_max, funn_max, morn_max, nigh_max, gym_max, insp_max, love_max, moti_max, roma_max, sing_max, sorr_max, work_max, Brea_max, Brot_max, Crea_max, Entr_max, Exam_max, Fath_max, Frie_max, Marr_max, Moth_max, Musi_max, Sist_max, Smar_max, Trus_max, What_max;
    FloatingActionButton fab;
    ScrollView sv;
    Dialog myDialog, exitDiag,update;
    Timer myTimer;
    Context context = this;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView, mAdViewTwo, mAdViewThree, mAdViewFour, mAdViewFive;
    String str_init;
    SharedPreferences app_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initz();
        CheckServerMaintainence();
        app_preferences = PreferenceManager
                .getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = app_preferences.edit();
        Bundle bundle = getIntent().getExtras();
        Boolean isFirstTime = bundle.getBoolean("app_preferences");
        String isFirstTime_str = Boolean.toString(isFirstTime);
        //  Toast.makeText(MainActivity.this,isFirstTime_str,Toast.LENGTH_SHORT).show();
        if (isFirstTime) {
            Loading();
            UpdateCheck();
            editor.putBoolean("isFirstTime", false);
            editor.commit();
        }
        LoadBanner();
        rootRef.child("Category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                anni_max = dataSnapshot.child("Anniversary").child("max").getValue(String.class);
                atti_max = dataSnapshot.child("Attitude").child("max").getValue(String.class);
                beau_max = dataSnapshot.child("Beautiful").child("max").getValue(String.class);
                birt_max = dataSnapshot.child("Birthday").child("max").getValue(String.class);
                chil_max = dataSnapshot.child("Children").child("max").getValue(String.class);
                coll_max = dataSnapshot.child("College").child("max").getValue(String.class);
                emot_max = dataSnapshot.child("Emotional").child("max").getValue(String.class);
                fami_max = dataSnapshot.child("Family").child("max").getValue(String.class);
                food_max = dataSnapshot.child("Food").child("max").getValue(String.class);
                funn_max = dataSnapshot.child("Funny").child("max").getValue(String.class);
                morn_max = dataSnapshot.child("Good_Morning").child("max").getValue(String.class);
                nigh_max = dataSnapshot.child("Good_Night").child("max").getValue(String.class);
                gym_max = dataSnapshot.child("Gym").child("max").getValue(String.class);
                insp_max = dataSnapshot.child("Inspirational").child("max").getValue(String.class);
                love_max = dataSnapshot.child("Love").child("max").getValue(String.class);
                moti_max = dataSnapshot.child("Motivational").child("max").getValue(String.class);
                roma_max = dataSnapshot.child("Romantic").child("max").getValue(String.class);
                sing_max = dataSnapshot.child("Single").child("max").getValue(String.class);
                sorr_max = dataSnapshot.child("Sorry").child("max").getValue(String.class);
                work_max = dataSnapshot.child("Anniversary").child("max").getValue(String.class);
                hind_max = dataSnapshot.child("Hindi").child("max").getValue(String.class);
                Brea_max = dataSnapshot.child("Break_up").child("max").getValue(String.class);
                Brot_max = dataSnapshot.child("Brother").child("max").getValue(String.class);
                Crea_max = dataSnapshot.child("Creative").child("max").getValue(String.class);
                Entr_max = dataSnapshot.child("Entrepreneur").child("max").getValue(String.class);
                Exam_max = dataSnapshot.child("Exam").child("max").getValue(String.class);
                Fath_max = dataSnapshot.child("Fathers_Day").child("max").getValue(String.class);
                Frie_max = dataSnapshot.child("Friends").child("max").getValue(String.class);
                Marr_max = dataSnapshot.child("Marriage").child("max").getValue(String.class);
                Moth_max = dataSnapshot.child("Mothers_Day").child("max").getValue(String.class);
                Musi_max = dataSnapshot.child("Music").child("max").getValue(String.class);
                Sist_max = dataSnapshot.child("Sister").child("max").getValue(String.class);
                Smar_max = dataSnapshot.child("Smart").child("max").getValue(String.class);
                Trus_max = dataSnapshot.child("Trust").child("max").getValue(String.class);
                What_max = dataSnapshot.child("Whatsapp").child("max").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final String uid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Typeface typeface = Typeface.createFromAsset(MainActivity.this.getAssets(), "rund.ttf");
        head.setTypeface(typeface);
        bottomBar.setActiveItem(1);
        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelect(int i) {
                if (i == 0) {
                    Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
                    intent.putExtra("uid", uid);
                    startActivity(intent);
                    Animatoo.animateSlideUp(MainActivity.this);
                } else if (i == 1) {

                } else if (i == 2) {
                    startActivity(new Intent(MainActivity.this, More.class));
                    Animatoo.animateSlideUp(MainActivity.this);

                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Hindi");
                intent.putExtra("uid", uid);
                intent.putExtra("max", hind_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
                LoadInitz();
            }
        });
        Anniversary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Anniversary");
                intent.putExtra("uid", uid);
                intent.putExtra("max", anni_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Attitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Attitude");
                intent.putExtra("uid", uid);
                intent.putExtra("max", atti_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Beautiful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Beautiful");
                intent.putExtra("uid", uid);
                intent.putExtra("max", beau_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
                LoadInitz();
            }
        });
        Birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Birthday");
                intent.putExtra("uid", uid);
                intent.putExtra("max", birt_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
                LoadInitz();
            }
        });
        Children.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Children");
                intent.putExtra("uid", uid);
                intent.putExtra("max", chil_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        College.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "College");
                intent.putExtra("uid", uid);
                intent.putExtra("max", coll_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Emotional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Emotional");
                intent.putExtra("uid", uid);
                intent.putExtra("max", emot_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);

            }
        });
        Family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Family");
                intent.putExtra("uid", uid);
                intent.putExtra("max", fami_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Food");
                intent.putExtra("uid", uid);
                intent.putExtra("max", food_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Funy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Funny");
                intent.putExtra("uid", uid);
                intent.putExtra("max", funn_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Good_Morning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Good Morning");
                intent.putExtra("uid", uid);
                intent.putExtra("max", morn_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
                LoadInitz();
            }
        });
        Good_Night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Good Night");
                intent.putExtra("uid", uid);
                intent.putExtra("max", nigh_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
                LoadInitz();
            }
        });
        Gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Gym");
                intent.putExtra("uid", uid);
                intent.putExtra("max", gym_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Inspirational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Inspirational");
                intent.putExtra("uid", uid);
                intent.putExtra("max", insp_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Love");
                intent.putExtra("uid", uid);
                intent.putExtra("max", anni_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
                LoadInitz();
            }
        });
        Motivational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Motivational");
                intent.putExtra("uid", uid);
                intent.putExtra("max", moti_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
                LoadInitz();
            }
        });
        Romantic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Romantic");
                intent.putExtra("uid", uid);
                intent.putExtra("max", roma_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
                LoadInitz();
            }
        });
        Single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Single");
                intent.putExtra("uid", uid);
                intent.putExtra("max", sing_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Sorry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Sorry");
                intent.putExtra("uid", uid);
                intent.putExtra("max", sorr_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
                LoadInitz();
            }
        });
        Work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Work");
                intent.putExtra("uid", uid);
                intent.putExtra("max", work_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });

        Break_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Break Up");
                intent.putExtra("uid", uid);
                intent.putExtra("max", Brea_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Brother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Brother");
                intent.putExtra("uid", uid);
                intent.putExtra("max", Brot_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Creative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Creative");
                intent.putExtra("uid", uid);
                intent.putExtra("max", Crea_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Entrepreneur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Entrepreneur");
                intent.putExtra("uid", uid);
                intent.putExtra("max", Entr_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Exam");
                intent.putExtra("uid", uid);
                intent.putExtra("max", Exam_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Fathers_Day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Father's Day");
                intent.putExtra("uid", uid);
                intent.putExtra("max", Fath_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Marriage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Marriage");
                intent.putExtra("uid", uid);
                intent.putExtra("max", Marr_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Mothers_Day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Mother's Day");
                intent.putExtra("uid", uid);
                intent.putExtra("max", Moth_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Music");
                intent.putExtra("uid", uid);
                intent.putExtra("max", Musi_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Sister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Sister");
                intent.putExtra("uid", uid);
                intent.putExtra("max", Sist_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Smart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Smart");
                intent.putExtra("uid", uid);
                intent.putExtra("max", Smar_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Trust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Trust");
                intent.putExtra("uid", uid);
                intent.putExtra("max", Trus_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
                LoadInitz();
            }
        });
        Children.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Children");
                intent.putExtra("uid", uid);
                intent.putExtra("max", chil_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
            }
        });
        Whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Quotes.class);
                intent.putExtra("Child", "Whatsapp");
                intent.putExtra("uid", uid);
                intent.putExtra("max", What_max);
                startActivity(intent);
                Animatoo.animateSlideUp(MainActivity.this);
                LoadInitz();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        sv.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            float y = 0;

            @SuppressLint("RestrictedApi")
            @Override
            public void onScrollChanged() {
                if (sv.getScrollY() > y) {
                    fab.setVisibility(View.VISIBLE);
                } else if (sv.getScrollY() == 0) {
                    fab.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void CheckServerMaintainence() {
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String ans =dataSnapshot.child("Maintenance").getValue(String.class);
                if (ans.equals("1"))
                {
                    TastyToast.makeText(getApplicationContext(), "Server is under maintenance...We are fixing up to serve you better", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    homeIntent.addCategory( Intent.CATEGORY_HOME );
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                    finishAndRemoveTask();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void LoadBanner() {
        rootRef.child("Admob").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String banner_alive = dataSnapshot.child("banner_alive").getValue(String.class);
                if (banner_alive == null) {
                    LoadBannerOne();
                    LoadBannerTwo();
                    LoadBannerThree();
                    LoadBannerFour();
                    LoadBannerFive();
                } else if (banner_alive.equalsIgnoreCase("on")) {
                    LoadBannerOne();
                    LoadBannerTwo();
                    LoadBannerThree();
                    LoadBannerFour();
                    LoadBannerFive();
                } else if (banner_alive.equalsIgnoreCase("off")) {

                } else {

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void LoadBannerThree() {
        mAdViewThree = findViewById(R.id.adViewThree);
        AdRequest adRequestFive = new AdRequest.Builder().build();
        mAdViewThree.loadAd(adRequestFive);
    }

    private void LoadBannerFour() {
        mAdViewFour = findViewById(R.id.adViewFour);
        AdRequest adRequestFour = new AdRequest.Builder().build();
        mAdViewFour.loadAd(adRequestFour);
    }

    private void LoadBannerFive() {
        mAdViewFive = findViewById(R.id.adViewFive);
        AdRequest adRequestFive = new AdRequest.Builder().build();
        mAdViewFive.loadAd(adRequestFive);
    }

    private void LoadBannerTwo() {
        mAdViewTwo = findViewById(R.id.adViewTwo);
        AdRequest adRequestTwo = new AdRequest.Builder().build();
        mAdViewTwo.loadAd(adRequestTwo);
    }

    private void LoadBannerOne() {
        mAdView = findViewById(R.id.adViewOne);
        AdRequest adRequestOne = new AdRequest.Builder().build();
        mAdView.loadAd(adRequestOne);
    }

    private void LoadData() {
        options = new FirebaseRecyclerOptions.Builder<DailyModel>().setQuery(rootRef.child("Daily_Quote"), DailyModel.class).build();
        adapter = new FirebaseRecyclerAdapter<DailyModel, DailyHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final DailyHolder holder, final int i, @NonNull DailyModel DailyModel) {
                holder.textView.setText("''" + DailyModel.getQuote() + "''");
                holder.cat.setText(DailyModel.getCategory());
                final Handler handler = new Handler();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Random rnd = new Random();
                        int color = Color.argb(180, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                        holder.rl.setBackgroundColor(color);
                    }
                });
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {

                    }
                }, 2000);
            }

            @NonNull
            @Override
            public DailyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_top, parent, false);
                return new DailyHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    private void initz() {
        myDialog = new Dialog(this);
        exitDiag = new Dialog(this);
        update=new Dialog(this);
        sv = findViewById(R.id.sv);
        fab = findViewById(R.id.topbtn);
        close = findViewById(R.id.close);
        head = findViewById(R.id.head_text);
        bottomBar = findViewById(R.id.bottomBar);
        rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.keepSynced(true);
        InitzAds();
        recyclerView = findViewById(R.id.daily_recycler_view);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        LoadData();
        Anniversary = findViewById(R.id.anniversary);
        Attitude = findViewById(R.id.attitude);
        Beautiful = findViewById(R.id.beautiful);
        Birthday = findViewById(R.id.birthday);
        Children = findViewById(R.id.children);
        College = findViewById(R.id.college);
        Emotional = findViewById(R.id.emotional);
        Hindi = findViewById(R.id.hindi);
        Family = findViewById(R.id.family);
        Food = findViewById(R.id.food);
        Funy = findViewById(R.id.funny);
        Good_Morning = findViewById(R.id.good_morning);
        Good_Night = findViewById(R.id.good_night);
        Gym = findViewById(R.id.gym);
        Inspirational = findViewById(R.id.inspirational);
        Love = findViewById(R.id.love);
        Motivational = findViewById(R.id.motivational);
        Romantic = findViewById(R.id.romantic);
        Single = findViewById(R.id.single);
        Sorry = findViewById(R.id.sorry);
        Work = findViewById(R.id.work);
        Break_up = findViewById(R.id.break_up);
        Brother = findViewById(R.id.brother);
        Creative = findViewById(R.id.creative);
        Entrepreneur = findViewById(R.id.entrepreneur);
        Exam = findViewById(R.id.exam);
        Fathers_Day = findViewById(R.id.father_day);
        Marriage = findViewById(R.id.marriage);
        Mothers_Day = findViewById(R.id.mother_day);
        Music = findViewById(R.id.music);
        Sister = findViewById(R.id.sister);
        Smart = findViewById(R.id.smart);
        Trust = findViewById(R.id.trust);
        Whatsapp = findViewById(R.id.whatsapp);
        ReadAdmobFirebase();

    }

    private void ReadAdmobFirebase() {
        rootRef.child("Admob").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String initz = dataSnapshot.child("inter").getValue(String.class);
                String banner = dataSnapshot.child("banner").getValue(String.class);
                str_init = initz;
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

    private void Loading() {
        myDialog.setContentView(R.layout.loading);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        myDialog.setCanceledOnTouchOutside(false);
        myTimer = new Timer();
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                myDialog.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 5000);
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Runnable SCROLLING_RUNNABLE = new Runnable() {

        @Override
        public void run() {
            recyclerView.smoothScrollBy(50, 0);
            mHandler.postDelayed(this, 10);
        }
    };

    private void LoadInitz() {
        rootRef.child("Admob").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String int_alive = dataSnapshot.child("interestitial_alive").getValue(String.class);
                if (int_alive == null) {
                    ShowInterestitialAd();
                } else if (int_alive.equalsIgnoreCase("on")) {
                    ShowInterestitialAd();
                } else if (int_alive.equalsIgnoreCase("off")) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ShowInterestitialAd() {
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

    @Override
    public void onBackPressed() {
        ShowExitDiag();
    }

    private void UpdateCheck() {
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.child("Update").getValue(String.class);
                if (value.equals("1")) {
                    ShowPopUp();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void ShowPopUp() {
        Button btnupdate;
        TextView diag_close;
        update.setContentView(R.layout.update_app_popup);
        btnupdate=update.findViewById(R.id.Update);
        diag_close=update.findViewById(R.id.close_diag);
        update.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        update.show();
        update.setCanceledOnTouchOutside(false);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
                }
            }
        });
        diag_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update.dismiss();
            }
        });
    }
    private void ShowExitDiag() {
        Button Yes, No;
        AdView adView;
        NativeExpressAdView mAdView;
        exitDiag.setContentView(R.layout.exit_pop_up);
        Yes = exitDiag.findViewById(R.id.yes);
        No = exitDiag.findViewById(R.id.no);
        adView = exitDiag.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                finishAffinity();
                exitDiag.dismiss();
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDiag.dismiss();
            }
        });
        exitDiag.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitDiag.show();
        exitDiag.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app_preferences = PreferenceManager
                .getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putBoolean("isFirstTime", true);
        editor.commit();
    }
}
