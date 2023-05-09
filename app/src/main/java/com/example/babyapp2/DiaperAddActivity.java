package com.example.babyapp2;

import static com.example.babyapp2.MyAdapter.x_child_id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DiaperAddActivity extends AppCompatActivity {
    RadioGroup radioGroupDiaperType;
    RadioButton rb_cloth, rb_disposable;
    CheckBox checkBoxPoop;
    CheckBox checkBoxBath;
    String diaperType = "unknown";
    Button btn_save, btn_cancel;
    boolean poopPresent = false;
    boolean bath = false;
    SQLdb db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaper_add);


        radioGroupDiaperType = findViewById(R.id.rg_diaper_type);
        checkBoxPoop = findViewById(R.id.cb_poop_present);
        checkBoxBath = findViewById(R.id.cb_bath);
        findId();
        db = new SQLdb(DiaperAddActivity.this);



        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                SQLdb db = new SQLdb(DiaperAddActivity.this);
                    if(rb_cloth.isChecked()){
                        db.addDiaperTrackerData(rb_cloth.getText().toString(),Boolean.getBoolean(String.valueOf(checkBoxPoop)), Boolean.getBoolean(String.valueOf(checkBoxBath)),x_child_id);;
                    }else{
                        db.addDiaperTrackerData(rb_disposable.getText().toString(),Boolean.getBoolean(String.valueOf(checkBoxPoop)), Boolean.getBoolean(String.valueOf(checkBoxBath)),x_child_id);;
                    }
                Intent intent = new Intent(DiaperAddActivity.this, Dashboard.class);
                startActivity(intent);
                }
        });

    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        if (view.getId() == R.id.rb_cloth){
            if (checked) {
                diaperType = getResources().getString(R.string.clothType);
            }
        }
        else if (view.getId() == R.id.rb_disposable){
            if (checked){
                diaperType = getResources().getString(R.string.disposableType);
            }
        }
    }

    public void onCheckBoxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        if (view.getId() == R.id.cb_poop_present){
            if (checked){
                poopPresent = true;
            }
        } else if (view.getId() == R.id.cb_bath){
            if (checked){
                bath = true;
            }
        }
    }

    void findId() {
        rb_cloth=(RadioButton) findViewById(R.id.rb_cloth);
        rb_disposable=(RadioButton) findViewById(R.id.rb_disposable);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);
    }

}