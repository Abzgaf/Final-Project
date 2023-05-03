package com.example.babyapp2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

public class Dashboard extends AppCompatActivity {

    Toolbar toolbar2;

    TextView textView;
    TextView textView2;
    ImageView imageView2;

    ImageView sleep_pic;
    TextView sleep_text;
    private CardView c1;

    ImageView diaper_pic;
    TextView diaper_text;
    private CardView c2;

    ImageView feed_pic;
    TextView feed_text;
    private CardView c3;

    ImageView gps_pic;
    TextView gps_text;
    private CardView c4;

    ImageView growth_pic;
    TextView growth_text;
    private CardView c5;

    ImageView ar_pic;
    TextView ar_text;
    private CardView c6;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar2 = findViewById(R.id.toolbar2);
        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLdb db = new SQLdb(Dashboard.this);

                /*if(db.checkTableEmpty()) {
                    Intent intent = new Intent(Dashboard.this, AddingActivity.class);
                    startActivity(intent);
                }else{}*/
                    Intent intent = new Intent(Dashboard.this, BabyList.class);
                    startActivity(intent);

                }
        });
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        imageView2 = findViewById(R.id.imageView2);
        sleep_pic = findViewById(R.id.sleep_pic);
        sleep_text = findViewById(R.id.sleep_text);
        c1 = findViewById(R.id.c1);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, SleepActivity.class);
                startActivity(intent);
            }
        });
        diaper_pic = findViewById(R.id.diaper_pic);
        diaper_text = findViewById(R.id.diaper_text);
        c2 = findViewById(R.id.c2);

        c2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, DiaperAddActivity.class);
                startActivity(intent);
            }
        });
        feed_pic = findViewById(R.id.feed_pic);
        feed_text = findViewById(R.id.feed_text);
        c3 = findViewById(R.id.c3);

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, FoodActivity.class);
                startActivity(intent);
            }
        });
        gps_pic = findViewById(R.id.gps_pic);
        gps_text = findViewById(R.id.gps_text);
        c4 = findViewById(R.id.c4);

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, GPSTracker.class);
                startActivity(intent);
            }
        });
        growth_pic = findViewById(R.id.growth_pic);
        growth_text = findViewById(R.id.growth_text);
        c5 = findViewById(R.id.c5);

        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, GPSTracker.class);
                startActivity(intent);
            }
        });
        ar_pic = findViewById(R.id.ar_pic);
        ar_text = findViewById(R.id.ar_text);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


    }
}