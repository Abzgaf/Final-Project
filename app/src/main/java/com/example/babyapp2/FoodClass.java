package com.example.babyapp2;

import java.io.Serializable;
import androidx.appcompat.app.AppCompatActivity;

public class FoodClass extends AppCompatActivity implements Serializable {

    private static final long serialVersionUID = 10L;

    //instance variables//
    private String foodName;
    private int calories;
    //    private String mealName;
    private int foodId;
    private String recordDate;

    //constructor//
    public FoodClass( String food, int cals, int id, String date){
        foodName = food;
        calories = cals;
//        mealName = meal;
        foodId = id;
        recordDate = date;

    }

    //default constructor - in case we dont want to add all variables to food object//
    public FoodClass(){
        //left empty//
    }

    //getters & setters for food object//

    //ID//
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    //Food name//
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    //Calories//
    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    //Meal//
//    public String getMealName() {
//        return mealName;
//    }
//
//    public void setMealName(String mealName) {
//        this.mealName = mealName;
//    }

    //Food id from db//
    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    //Date//
    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}

