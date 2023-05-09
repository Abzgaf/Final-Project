package com.example.babyapp2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {

    private SQLdb db;
    private ArrayList<FoodClass> dbFoods = new ArrayList<>();
    private FoodAdapter foodAdapter;
    private ListView listView;

    private FoodClass myFood;
    private TextView totalCals, totalFoods;


    //displaying total cals & total foods//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list_view);

        listView = (ListView) findViewById(R.id.list_breakfast);
        totalCals = (TextView) findViewById(R.id.totalAmountTextView);
        totalFoods = (TextView) findViewById(R.id.totalItemsTextView);

        FoodList();
    }

    //populating our total food and total cals and listView//
    private void FoodList() {
        //clear ArrayList//
        dbFoods.clear();
        db = new SQLdb(getApplicationContext());
        ArrayList<FoodClass> foodsFromDB = db.getFoods();
        int calsValue = db.totalCalories();
        int totalItems = db.getTotalFoodItems();
        String formattedValue = FoodFormatDec.formatNumber(calsValue);//formatting the numbers//
        String formattedItems = FoodFormatDec.formatNumber(totalItems);
        totalCals.setText("Total Calories: " + formattedValue);
        totalFoods.setText("Total Foods: " + formattedItems);

        for (int i = 0; i < foodsFromDB.size(); i++){//a loop to go through all of the food list

            String name = foodsFromDB.get(i).getFoodName();
            String dateText = foodsFromDB.get(i).getRecordDate();
            int cals = foodsFromDB.get(i).getCalories();
            int foodId = foodsFromDB.get(i).getFoodId();

            //new instance of food//
            myFood = new FoodClass();
            myFood.setFoodName(name);
            myFood.setRecordDate(dateText);
            myFood.setCalories(cals);
            myFood.setFoodId(foodId);

            //add myFood object to dbFoods arrayList//
            dbFoods.add(myFood);

        }
        db.close();

        //setup adapter//
        //adaptor takes in Display Food and XML file we want to inflate - list_row & data//
        foodAdapter = new FoodAdapter(FoodActivity.this, R.layout.food_row, dbFoods);
        listView.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();

    }


    //Show menu in display food//
    //takes menu view parameter//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.food_menu, menu);
        //we return true to tell android we have provided a menu//
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
