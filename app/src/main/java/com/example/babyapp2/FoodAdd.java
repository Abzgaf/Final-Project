package com.example.babyapp2;

// FoodAdd class that allows users to add food items to a database by entering the name of the food item and its calorie count and clicking a submit button.

// The class initializes a database object and retrieves the EditText fields and submit button from the layout file.
// When the submit button is clicked, the saveDataToDB method is called, which retrieves the text from the EditText fields,
// creates a new FoodClass object with the entered data, adds the object to the database using the addFoodTrackerData method, and then clears the EditText fields.
// Finally, the user is redirected to the FoodActivity class to view their added food items.
// If the user does not enter any data, a toast message is displayed prompting them to enter the required information.

import static com.example.babyapp2.MyAdapter.x_child_id;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FoodAdd extends AppCompatActivity {

    EditText food_name, food_cal;
    Button btn_submit;

    private SQLdb db;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_add);

        db = new SQLdb(FoodAdd.this);

        food_name = findViewById(R.id.food_txt);
        food_cal = findViewById(R.id.cal_txt);
        btn_submit = findViewById(R.id.btn_submit);

        Intent intent = getIntent();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FoodDataDB();
            }
        });
    }


    private void FoodDataDB(){

        FoodClass food = new FoodClass();
        String name = food_name.getText().toString().trim();
        String cal = this.food_cal.getText().toString().trim();
        int int_calories = Integer.parseInt(cal);

        if (name.equals("") || cal.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter food/beverage ", Toast.LENGTH_SHORT).show();
        }else{
            food.setFoodName(name);
            food.setCalories(int_calories);

            db.addFoodTrackerData(food, x_child_id);
            db.close();

            food_name.setText("");
            this.food_cal.setText("");

            startActivity(new Intent(FoodAdd.this, FoodActivity.class));

        }
    }


}
