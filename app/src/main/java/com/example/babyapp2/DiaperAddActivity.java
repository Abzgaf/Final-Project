package com.example.babyapp2;

// Functionality class of the Diaper Add activity
// It includes various UI components such as radio buttons for selecting the type of diaper,
// checkboxes for indicating whether poop is present and whether a bath was taken, and two buttons for saving or canceling the diaper entry.
//
// The code sets up listeners for the radio buttons and checkboxes to capture the user's selections.
// If the user selects the cloth diaper radio button, the diaper type is set to "cloth", otherwise it is set to "disposable".
// Similarly, if the user checks the poop present checkbox, a flag is set to indicate that poop is present, and if the bath taken checkbox is checked,
// a flag is set to indicate that a bath was taken.
//
// When the user clicks the save button, the code adds the diaper entry to a SQLite database using the values selected by the user.
// It then launches the Dashboard activity. If the user clicks the cancel button, the code simply returns to the Dashboard activity without saving any data.

import static com.example.babyapp2.MyAdapter.x_child_id;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaper_add);


        radioGroupDiaperType = findViewById(R.id.DiaperGroup);
        checkBoxPoop = findViewById(R.id.poopNappieBox);
        checkBoxBath = findViewById(R.id.bathTakenBox);
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

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DiaperAddActivity.this,  Dashboard.class);
                startActivity(i);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        if (view.getId() == R.id.cloth){
            if (checked) {
                diaperType = getResources().getString(R.string.clothType);
            }
        }
        else if (view.getId() == R.id.disposable){
            if (checked){
                diaperType = getResources().getString(R.string.disposableType);
            }
        }
    }

    public void onCheckBoxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        if (view.getId() == R.id.poopNappieBox){
            if (checked){
                poopPresent = true;
            }
        } else if (view.getId() == R.id.bathTakenBox){
            if (checked){
                bath = true;
            }
        }
    }

    void findId() {
        rb_cloth=(RadioButton) findViewById(R.id.cloth);
        rb_disposable=(RadioButton) findViewById(R.id.disposable);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);
    }

}