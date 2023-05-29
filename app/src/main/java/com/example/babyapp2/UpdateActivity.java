package com.example.babyapp2;

// UpdateActivity class that is used on the MainActivity.
// It provides functionalities to update or delete the baby's details, such as the baby's name, age, and gender.

//1. The activity layout includes EditTexts for baby's name and age, a TextView for baby's gender, and buttons for updating and deleting the baby's details.

//2. `getSetData()` function retrieves the baby's details from the intent and sets them as the initial values in the EditTexts and TextView.

//3. When the `update_button` is clicked, the `onClick()` function updates the baby's details in the SQL database
//   using the `SQLdb` class and navigates back to the `BabyList` activity.

//4. When the `delete_button` is clicked, a confirmation dialog is shown using the `deleteConfirmation()` function.
//   If the user confirms to delete the details, the baby's data will be deleted from the SQL database using the `SQLdb` class, and the activity will be finished.

//5. When the `baby_gender_upd` TextView is clicked, an AlertDialog with multiple choices (Boy and Girl) is displayed.
//   The user can select one, and the selected gender will be set as the text in the TextView.
//   The DialogInterface buttons include "OK" to confirm the selection, "Cancel" to dismiss the dialog, and "Clear All" to clear the selected gender.


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.babyapp2.MainActivity.x;

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

public class UpdateActivity extends AppCompatActivity {

    EditText baby_name_upd;
    EditText baby_age_upd;
    TextView baby_gender_upd;
    Button update_button, delete_button;

    String id, baby_name, baby_gender;
    Integer baby_age;

    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {"Boy", "Girl"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        baby_name_upd = findViewById(R.id.baby_name_upd);
        baby_age_upd = findViewById(R.id.baby_age_upd);
        baby_gender_upd = findViewById(R.id.baby_gender_upd);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        getSetData();

        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setTitle(baby_name);
        }
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteConfirmation();

            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLdb db = new SQLdb(UpdateActivity.this);
                baby_name = baby_name_upd.getText().toString().trim();
                baby_age = Integer.valueOf(baby_age_upd.getText().toString().trim());
                baby_gender = baby_gender_upd.getText().toString().trim();
                db.updateTable(id, baby_name, baby_age, baby_gender);
                Intent intent = new Intent(UpdateActivity.this, BabyList.class);
                startActivity(intent);
            }
        });



        baby_gender_upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);

                // set title
                builder.setTitle("Select gender");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            langList.add(i);
                            // Sort array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(langArray[langList.get(j)]);
                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        baby_gender_upd.setText(stringBuilder.toString());
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
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            baby_gender_upd.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();

            }
        });

    }

    void getSetData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("age") && getIntent().hasExtra("gender")) {
            id = getIntent().getStringExtra("id");
            baby_name = getIntent().getStringExtra("name");
            baby_age = Integer.valueOf(getIntent().getStringExtra("age"));
            baby_gender = getIntent().getStringExtra("gender");

            baby_name_upd.setText(baby_name);
            baby_age_upd.setText(String.valueOf(baby_age));
            baby_gender_upd.setText(baby_gender);

        } else {
            Toast.makeText(UpdateActivity.this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteConfirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
        builder.setTitle("Delete " + baby_name + " ?");
        builder.setMessage("Are you sure you want to delete " + baby_name + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLdb db = new SQLdb(UpdateActivity.this);
                db.deleteRow(id);
                finish();
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
