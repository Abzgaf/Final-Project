package com.example.babyapp2;

// Android class that displays a list of babies.
// It uses a RecyclerView to display the list and allows the user to add new babies or delete all existing ones.

// The screen also uses a FloatingActionButton to allow the user to add a new baby,
// an ImageView and TextView to display a message if there are no babies in the list.

// The SQLdb class is used to manage the database that stores the baby information,
// and the showData() method retrieves the information from the database and adds it to the appropriate ArrayLists.

// The MyAdapter class is used to manage the display of the baby information in the RecyclerView.

// The onCreateOptionsMenu() method creates a menu for deleting all babies, and the onOptionsItemSelected() method confirms the user's decision to delete all records.

// Lastly, the deleteAllConfirmation() method shows a confirmation dialog box when the user chooses to delete all babies.

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class BabyList extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add;
    ImageView cry_img;
    TextView nodata_txt;

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
        cry_img = findViewById(R.id.cry_img);
        nodata_txt = findViewById(R.id.nodata_txt);
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
            cry_img.setVisibility(View.VISIBLE);
            nodata_txt.setVisibility(View.VISIBLE);
        }else{
            while (c.moveToNext()){
                id.add(c.getString(0));
                baby_name.add(c.getString(1));
                baby_age.add(c.getString(2));
                baby_gender.add(c.getString(3));
            }
            cry_img.setVisibility(View.GONE);
            nodata_txt.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.m, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            deleteAllConfirmation();
        }
        return super.onOptionsItemSelected(item);
    }

    void deleteAllConfirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(BabyList.this);
        builder.setTitle("Delete All");
        builder.setMessage("Are you sure you want to delete all baby records? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLdb db = new SQLdb(BabyList.this);
                db.deleteAllNames();
                recreate();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }


}