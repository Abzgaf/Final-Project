package com.example.babyapp2;

// FoodClass" that implements Serializable.

// It has a constructor that takes in values for each of these variables, as well as a default constructor with no arguments.
// The class has getter and setter methods for each of the private variables.
// Class is used to represent a type of food with associated calorie information, and can be serialized for storage or transmission purposes.

import java.io.Serializable;
import androidx.appcompat.app.AppCompatActivity;

public class FoodClass extends AppCompatActivity implements Serializable {

    private static final long serialVersionUID = 10L;


    private String foodName;
    private int calories;
    private int foodId;
    private String recordDate;

    public FoodClass( String food, int cals, int id, String date){
        foodName = food;
        calories = cals;
        foodId = id;
        recordDate = date;

    }

    public FoodClass(){

    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}

