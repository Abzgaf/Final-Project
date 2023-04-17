package com.example.babyapp2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class BabyList extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add;

    SQLdb db;
    ArrayList<String> id;
    ArrayList<String> baby_name;
    ArrayList<String> baby_age;
    ArrayList<String> baby_gender;
    MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addscreen);

        recyclerView = findViewById(R.id.recyclerView);
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BabyList.this, AddingActivity.class);
                startActivity(intent);
            }
        });

        db = new SQLdb(BabyList.this);
        id = new ArrayList<>();
        baby_name = new ArrayList<>();
        baby_age = new ArrayList<>();
        baby_gender = new ArrayList<>();
        showData();
        myAdapter = new MyAdapter(BabyList.this, BabyList.this, id, baby_name, baby_age, baby_gender);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(BabyList.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void showData(){
        Cursor c = db.getProfileData();
        if(c.getCount() == 0){
            Toast.makeText(this, "Table is empty", Toast.LENGTH_SHORT).show();
        }else{
            while (c.moveToNext()){
                id.add(c.getString(0));
                baby_name.add(c.getString(1));
                baby_age.add(c.getString(2));
                baby_gender.add(c.getString(3));
            }
        }
    }
}