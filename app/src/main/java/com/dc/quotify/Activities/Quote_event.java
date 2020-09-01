package com.dc.quotify.Activities;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.database.ValueEventListener;
import com.dc.quotify.R;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Random;

public class Quote_event extends AppCompatActivity {

    TextView quote;
    ImageView like, share, copy, back, save, save_img,image;
    DatabaseReference rootRef;
    String global_str,global_quote;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    SharedPreferences app_preferences;
    String str_init;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_event);
        final String Datakey = getIntent().getStringExtra("DataKey");
        final String Child = getIntent().getStringExtra("Child");
        final String uid=getIntent().getStringExtra("uid");
        rootRef = FirebaseDatabase.getInstance().getReference();
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
        InitzAds();
        LoadBanner();
        quote = findViewById(R.id.text_view);
        like = findViewById(R.id.like);
        share = findViewById(R.id.share);
        copy = findViewById(R.id.copy);
        back = findViewById(R.id.back);
        save = findViewById(R.id.save);
        save_img = findViewById(R.id.image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 onBackPressed();
            }
        });
        quote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer mp = MediaPlayer.create(Quote_event.this, R.raw.click_back);
                mp.start();
                Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                quote.setBackgroundColor(color);
            }
        });
        rootRef.child("Category").child(Child).child(Datakey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String str_quote = dataSnapshot.child("quote").getValue(String.class);
                global_quote=str_quote+"\n\n❤“Quotify”®";
                quote.setText(global_quote);
                if (str_quote.contains(" ")) {
                    String img_str_quote = Child+Datakey;
                    global_str = img_str_quote;
                }

                copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Quote", str_quote);
                        clipboard.setPrimaryClip(clip);
                        TastyToast.makeText(Quote_event.this,"Successfully copied quote",Toast.LENGTH_SHORT,TastyToast.SUCCESS);
                    }
                });
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       Share();
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rootRef.child("Admob").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String int_alive =dataSnapshot.child("interestitial_alive").getValue(String.class);
                                if (int_alive==null)
                                {
                                    LoadInitz();
                                    Save();
                                }
                                else if (int_alive.equalsIgnoreCase("on"))
                                {
                                    LoadInitz();
                                    Save();
                                }
                                else if (int_alive.equalsIgnoreCase("off"))
                                {
                                    Save();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });
                final MediaPlayer mp = MediaPlayer.create(Quote_event.this, R.raw.all);
                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rootRef.child("Users").child(uid).child(Datakey).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String like_check = dataSnapshot.child("like").getValue(String.class);
                                if (like_check == null) {
                                    like.setImageResource(R.drawable.ic_heart);
                                    mp.start();
                                    TastyToast.makeText(Quote_event.this,"Liked this quote.....",Toast.LENGTH_SHORT,TastyToast.SUCCESS);
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("quote", global_quote);
                                    map.put("like", "true");
                                    rootRef.child("Users").child(uid).child(Datakey).setValue(map);
                                }
                                else
                                {

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                rootRef.child("Users").child(uid).child(Datakey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String like_check =dataSnapshot.child("like").getValue(String.class);
                        if (like_check!=null&&like_check.equals("true"))
                        {
                            like.setImageResource(R.drawable.ic_heart);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void Save() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                !=PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }else{
            quote.setDrawingCacheEnabled(true);
            Bitmap bm = quote.getDrawingCache();
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bm);
            image = findViewById(R.id.image);
            image.setBackgroundColor(Color.BLACK);
            image.setBackgroundDrawable(bitmapDrawable);
            SaveImageToGallery();
        }
    }

    private void Share() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                !=PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }else {
            quote.setDrawingCacheEnabled(true);
            Bitmap bm = quote.getDrawingCache();
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bm);
            image = findViewById(R.id.image);
            image.setBackgroundColor(Color.BLACK);
            image.setBackgroundDrawable(bitmapDrawable);
            View content = findViewById(R.id.image);
            content.setDrawingCacheEnabled(true);
            //  Toast.makeText(Quote_event.this, "Share clicked", Toast.LENGTH_LONG).show();
            Bitmap bitmap = content.getDrawingCache();
            File root = Environment.getExternalStorageDirectory();
            File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/" + global_str + ".Quotify.jpg");
            try {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                cachePath.createNewFile();
                FileOutputStream ostream = new FileOutputStream(cachePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                ostream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/*");
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cachePath));
            startActivity(Intent.createChooser(share, "Share via"));
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode ==1000){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                TastyToast.makeText(Quote_event.this,"Permission successfully granted...Now you can download or share",Toast.LENGTH_SHORT,TastyToast.SUCCESS);

            }
            else
            {
                TastyToast.makeText(Quote_event.this,"Please allow us to download..",Toast.LENGTH_SHORT,TastyToast.CONFUSING);
                finish();
            }
        }
    }

    private void SaveImageToGallery() {
        View content = findViewById(R.id.image);
        content.setDrawingCacheEnabled(true);
        Bitmap bitmap = content.getDrawingCache();
        File root = Environment.getExternalStorageDirectory();
        File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/" + global_str + ".Quotify.jpg");
        try {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            cachePath.createNewFile();
            FileOutputStream ostream = new FileOutputStream(cachePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            ostream.close();
            TastyToast.makeText(Quote_event.this,"Image is saved to DCIM/Camera/"+global_str+".Quotify.jpg",Toast.LENGTH_SHORT,TastyToast.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            TastyToast.makeText(Quote_event.this,"Error:"+e,Toast.LENGTH_SHORT,TastyToast.ERROR);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        return;
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        app_preferences = PreferenceManager
                .getDefaultSharedPreferences(Quote_event.this);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putBoolean("isFirstTime", true);
        editor.commit();
    }
}
