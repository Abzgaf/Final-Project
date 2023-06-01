package com.example.babyapp2;

// FoodDesc class that displays the details of the food item.
// It has three TextViews to display the food name, calories, and record date.
// The food item details are obtained from an intent extra passed from FoodClass.
// The class also has an options menu that allows the user to delete the food item.
// Delete option is inflated using a MenuInflater
// When the user selects the delete option, a confirmation dialog is displayed.
// If the user confirms, the food item is deleted from the database and the user is redirected to the FoodActivity.

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class FoodDesc extends AppCompatActivity {

    private TextView food_name_desc, food_cal_desc, food_date_desc;
    private int foodId;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_desc);

        food_name_desc = findViewById(R.id.food_name_desc);
        food_cal_desc = findViewById(R.id.food_cal_desc);
        food_date_desc = findViewById(R.id.food_date_desc);

        FoodClass food = (FoodClass) getIntent().getSerializableExtra("userObj");

        food_name_desc.setText(food.getFoodName());
        food_cal_desc.setText(String.valueOf(food.getCalories()));
        food_date_desc.setText(food.getRecordDate());
        foodId = food.getFoodId();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_all) {
            AlertDialog.Builder alert = new AlertDialog.Builder(FoodDesc.this);
            alert.setTitle("Delete Item?");
            alert.setMessage("Are you sure you want to delete this item?");
            alert.setNegativeButton("No", null);
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    SQLdb dba = new SQLdb(getApplicationContext());
                    dba.deleteFoodRow(foodId);

                    Toast.makeText(getApplicationContext(), "Food Item Deleted!", Toast.LENGTH_LONG)
                            .show();

                    startActivity(new Intent(FoodDesc.this, FoodActivity.class));
                    FoodDesc.this.finish();
                }
            });
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
