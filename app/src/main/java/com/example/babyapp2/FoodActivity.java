package com.example.babyapp2;

// FoodActivity class that displays a list of food items and their corresponding calorie count.

// The class has several private variables such as a SQL database object, an ArrayList of FoodClass objects, a FoodAdapter object, a ListView object
// , and TextView objects for displaying the total calories and total food items.


// It then calls the FoodList() method, which retrieves food items from the SQL database and populates the ArrayList with them.
// The total calorie count and total number of food items are also calculated and displayed using the TextView objects.

// The FoodAdapter object is then created using the FoodAdapter class,
// which is responsible for inflating the food_row layout and populating it with data from the ArrayList.
// The ListView object is then set to use the FoodAdapter object to display the food items.

// The class overrides the onCreateOptionsMenu() and onOptionsItemSelected() methods to create a menu with options to navigate back to the home screen,
// view the food diary, or add a new food item.

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {

    private SQLdb db;
    private ArrayList<FoodClass> FoodItemDb = new ArrayList<>();
    private FoodAdapter foodAdapter;
    private ListView food_list;
    private FoodClass myFood;
    private TextView totalCalories, totalFoodItems;


    //display total cals & total foods//
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list_view);

        food_list = findViewById(R.id.food_list);
        totalCalories =  findViewById(R.id.totalCalories);
        totalFoodItems = findViewById(R.id.totalFoodItems);
        FoodList();
    }


    private void FoodList() {

        FoodItemDb.clear();
        db = new SQLdb(getApplicationContext());
        ArrayList<FoodClass> foodsFromDB = db.getFoods();
        int calsValue = db.totalCalories();
        int totalItems = db.getTotalFoodItems();
        String formattedValue = FoodFormatDec.formatNumber(calsValue);
        String formattedItems = FoodFormatDec.formatNumber(totalItems);
        totalCalories.setText("Total Calories: " + formattedValue);
        totalFoodItems.setText("Total Foods: " + formattedItems);

        for (int i = 0; i < foodsFromDB.size(); i++){

            String name = foodsFromDB.get(i).getFoodName();
            String dateText = foodsFromDB.get(i).getRecordDate();
            int cals = foodsFromDB.get(i).getCalories();
            int foodId = foodsFromDB.get(i).getFoodId();


            myFood = new FoodClass();
            myFood.setFoodName(name);
            myFood.setRecordDate(dateText);
            myFood.setCalories(cals);
            myFood.setFoodId(foodId);


            FoodItemDb.add(myFood);

        }
        db.close();


        //adaptor takes in Display Food and XML file we want to inflate - list_row & data//
        foodAdapter = new FoodAdapter(FoodActivity.this, R.layout.food_row, FoodItemDb);
        food_list.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.food_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intentHome = new Intent(this, Dashboard.class);
        Intent intentDiary = new Intent(this, FoodActivity.class);
        Intent intentFoodList = new Intent(FoodActivity.this, FoodAdd.class);

        switch (item.getItemId()) {

            case R.id.homeBtn: startActivity(intentHome);
                return true;

            case R.id.action_diary: startActivity(intentDiary);
            return true;

            case R.id.action_addFood: startActivity(intentFoodList);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }
}
