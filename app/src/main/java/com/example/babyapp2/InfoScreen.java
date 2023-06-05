package com.example.babyapp2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class InfoScreen extends AppCompatActivity {

    public TextView heading, info_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_screen);

        heading = findViewById(R.id.heading);
        info_txt = findViewById(R.id.info_txt);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }
}
