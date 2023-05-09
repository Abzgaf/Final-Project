package com.example.babyapp2;

import static com.example.babyapp2.MyAdapter.x_child_id;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FoodAdd extends AppCompatActivity {

    EditText foodName, foodCals;
    Button mSubmitButton;

    private SQLdb db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_add);

        db = new SQLdb(FoodAdd.this);

        foodName = (EditText)findViewById(R.id.foodEditText);
        foodCals = (EditText)findViewById(R.id.caloriesEditText);
        mSubmitButton = (Button)findViewById(R.id.submitButton);

        Intent intent = getIntent();

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveDataToDB();
            }
        });
    }

    //saving user input to db//
    private void saveDataToDB(){

        FoodClass food = new FoodClass();
        String name = foodName.getText().toString().trim();
        String calsString = foodCals.getText().toString().trim();
        int cals = Integer.parseInt(calsString);

        if (name.equals("") || calsString.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter food/beverage ", Toast.LENGTH_SHORT).show();
        }else{
            food.setFoodName(name);
            food.setCalories(cals);

            db.addFoodTrackerData(food, x_child_id);
            db.close();

            //clear the form for next session//
            foodName.setText("");
            foodCals.setText("");

            //take users to next screen (display all entered items)//
            startActivity(new Intent(FoodAdd.this, FoodActivity.class));

        }
    }


}
