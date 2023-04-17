package com.example.babyapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {

    private ArrayList<Food> foodList = new ArrayList<>();

    private RecyclerView recyclerView;
    private FoodAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FoodAdapter(this, foodList);
        recyclerView.setAdapter(adapter);

        // Add some sample food items to the list
        foodList.add(new Food("Banana", 105));
        foodList.add(new Food("Apple", 95));
        foodList.add(new Food("Orange", 62));
        foodList.add(new Food("Grapes", 69));
        foodList.add(new Food("Pineapple", 452));

        // Notify the adapter that the data set has changed
        adapter.notifyDataSetChanged();
    }
}