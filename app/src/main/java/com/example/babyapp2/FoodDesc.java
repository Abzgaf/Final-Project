package com.example.babyapp2;

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

    private TextView foodName, calories, dateTaken;
    private int foodId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_desc);

        foodName = (TextView) findViewById(R.id.detsFoodName);
        calories = (TextView) findViewById(R.id.detsCaloriesValue);
        dateTaken = (TextView) findViewById(R.id.detsDateText);

        //has serializable food object - all details//
        //pass in key "userObj" from CustomListViewAdaptor//
        FoodClass food = (FoodClass) getIntent().getSerializableExtra("userObj");

        foodName.setText(food.getFoodName());
        calories.setText(String.valueOf(food.getCalories()));
        dateTaken.setText(food.getRecordDate());

        //getting food id so we are able to delete it//
        foodId = food.getFoodId();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar//
        getMenuInflater().inflate(R.menu.food_menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get item id so it can be deleted//
        int id = item.getItemId();

        //refers to deleteItem from menu//
        if (id == R.id.deleteItem) {

            AlertDialog.Builder alert = new AlertDialog.Builder(FoodDesc.this);
            alert.setTitle("Delete?");
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

                    //remove this activity from activity stack//
                    FoodDesc.this.finish();
                }
            });
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
