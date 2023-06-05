package com.example.babyapp2;

// Android activity that allows the user to add a baby profile to a database.

// The activity contains several UI elements such as EditTexts for the baby's name and age,
// a TextView for selecting the baby's gender, and a Button for adding the baby to the database.

// The code initializes an AlertDialog when the user clicks on the gender TextView,
// which allows the user to select the baby's gender from a list of options (in this case, "Boy" and "Girl").
// The AlertDialog uses a boolean array to keep track of which options are selected, and an ArrayList to store the selected options.
// When the user clicks the "OK" button in the dialog, the selected gender options are concatenated into a string and displayed in the gender TextView.

// When the user clicks the "Add" button, the code reads the values from the EditTexts and TextView, and adds them to the database using an SQLdb object.
// It then starts a new activity that displays a list of all the babies in the database.

import static com.example.babyapp2.MainActivity.x;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class AddingActivity extends AppCompatActivity {

    EditText baby_name;
    EditText baby_age;
    TextView baby_gender;
    Button add_button;

    boolean[] selectedLanguage;
    ArrayList<Integer> genderList = new ArrayList<>();
    String[] genderArray = {"Boy", "Girl"};



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        baby_name = findViewById(R.id.baby_name);
        baby_age = findViewById(R.id.baby_age);
        baby_gender = findViewById(R.id.baby_gender);
        add_button = findViewById(R.id.add_button);
        selectedLanguage = new boolean[genderArray.length];

        baby_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(AddingActivity.this);
                builder.setTitle("Select gender");
                builder.setCancelable(false);

                builder.setMultiChoiceItems(genderArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            genderList.add(i);
                            // Sort array list
                            Collections.sort(genderList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            genderList.remove(Integer.valueOf(i));
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int j = 0; j < genderList.size(); j++) {
                            // concat array value
                            stringBuilder.append(genderArray[genderList.get(j)]);
                            if (j != genderList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        baby_gender.setText(stringBuilder.toString());
                    }
                });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddingActivity.this, x, Toast.LENGTH_SHORT).show();
                SQLdb db = new SQLdb(AddingActivity.this);
                db.addBabyProfile(baby_name.getText().toString().trim(),
                        Integer.valueOf(baby_age.getText().toString().trim()),
                        baby_gender.getText().toString().trim(),x);
                Intent intent = new Intent(AddingActivity.this, BabyList.class);
                startActivity(intent);
            }
        });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            selectedLanguage[j] = false;
                            genderList.clear();
                            baby_gender.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });
    }
}