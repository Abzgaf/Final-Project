package com.example.babyapp2;
import static com.example.babyapp2.MainActivity.x;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


public class SQLdb extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BabyApp.db";
    private static final int DATABASE_VERSION = 1;



    private final ArrayList<FoodClass> foodList = new ArrayList<>();
    private Context context;
    private static final String TABLE_NAME = "login_dets";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "username";
    private static final String COLUMN_PASS = "password";

    private static final String TABLE_NAME2 = "baby_dets";
    private static final String COLUMN_NAME2 = "baby_name";
    private static final String COLUMN_AGE = "baby_age";
    private static final String COLUMN_GENDER = "baby_gender";
    private static final String COLUMN_PARENT_NAME= "parent_name";


    private static final String TABLE_NAME3 = "sleep_tracker";
    private static final String COLUMN_X = "xValue";
    private static final String COLUMN_Y = "yValue";

    private static final String TABLE_NAME4 = "diaper_tracker";
    private static final String COLUMN_MATERIAL = "material";
    private static final String COLUMN_POOP = "poop";
    private static final String COLUMN_BATH= "bath_done";


    public static final String TABLE_NAME5 = "food_tracker";
    public static final String COLUMN_FOOD = "name";
    public static final String COLUMN_CAL = "calories";
    public static final String COLUMN_DATE = "recorddate";
    public static final String FOOD_ROW_ID = "food_row_id";




    SQLdb(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_PASS + " TEXT);";
        db.execSQL(query);

        String query2 =
                "CREATE TABLE " + TABLE_NAME2 +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME2 + " TEXT, " +
                        COLUMN_AGE + " INTEGER, " +
                        COLUMN_GENDER + " TEXT, " +
                        COLUMN_PARENT_NAME + " TEXT);";
        db.execSQL(query2);


        String query3 =
                "CREATE TABLE " + TABLE_NAME3 +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_X + " INTEGER, " +
                        COLUMN_Y + " INTEGER, " +
                        COLUMN_NAME2 + " TEXT);";
        db.execSQL(query3);

        String query4 =
                "CREATE TABLE " + TABLE_NAME4 +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_MATERIAL + " TEXT, " +
                        COLUMN_POOP + " BOOLEAN, " +
                        COLUMN_BATH + " BOOLEAN, " +
                        COLUMN_PARENT_NAME + " TEXT);";
        db.execSQL(query4);

        String query5 =
                "CREATE TABLE " + TABLE_NAME5 +
                            " (" + FOOD_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_FOOD + " TEXT, " +
                        COLUMN_CAL + " INT, " +
                        COLUMN_DATE + " LONG, " +
                        COLUMN_PARENT_NAME + " TEXT);";
        db.execSQL(query5);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME5);
        onCreate(db);
    }

    void addData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, username);
        values.put(COLUMN_PASS, password);
        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1) {
            Toast.makeText(context, "Failed to create account", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Account created", Toast.LENGTH_SHORT).show();
        }
    }

    void addBabyProfile(String name, int age, String gender, String x) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME2, name);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_PARENT_NAME, x);
        long result = db.insert(TABLE_NAME2, null, values);
        if (result == -1) {
            Toast.makeText(context, "Failed to add description", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Description added", Toast.LENGTH_SHORT).show();
        }
    }

    void addSleepTrackerData(Date x, Date y, String x_babyName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_X, String.valueOf(x));
        values.put(COLUMN_Y, String.valueOf(y));
        values.put(COLUMN_NAME2, x_babyName);
        long result = db.insert(TABLE_NAME3, null, values);
        if (result == -1) {
            Toast.makeText(context, "Failed to add tracker data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Tracker data created", Toast.LENGTH_SHORT).show();
        }
    }

    void addDiaperTrackerData(String material, boolean poop, boolean bath, String x_babyname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_MATERIAL, material);
        values.put(COLUMN_POOP, poop);
        values.put(COLUMN_BATH, bath);
        values.put(COLUMN_PARENT_NAME, x_babyname);
        long result = db.insert(TABLE_NAME4, null, values);
        if (result == -1) {
            Toast.makeText(context, "Failed to add diaper description", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Diaper description added", Toast.LENGTH_SHORT).show();
        }
    }

    void addFoodTrackerData(FoodClass food, String x_babyname){
        SQLiteDatabase dba = this.getWritableDatabase();

        //key value pair object//
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOOD, food.getFoodName());
        values.put(COLUMN_CAL, food.getCalories());
        //ask Android OS to give date//
        values.put(COLUMN_DATE, System.currentTimeMillis());
        values.put(COLUMN_PARENT_NAME, x_babyname);

        //inserting food into our table//
        dba.insert(TABLE_NAME5, null, values);

        //checking if food object has been added to the db//
        Log.v("Added Food item", "It worked!!");

        //close db//
        dba.close();

    }

    public Boolean checkusername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from login_dets where username = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            Toast.makeText(context, "Logged in", Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(context, "Failed to Log in ", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    Cursor getProfileData(){
        String query = "SELECT * FROM  baby_dets where parent_name ='"+ x +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        if(db != null){
                c = db.rawQuery(query, null);
        }
        return c;
    }

    public int getTotalFoodItems() {

        int totalItems = 0;

        //selecting all from our table//
        String query = "SELECT * FROM " + TABLE_NAME5;
        //SQLite db instance - so we are able to use our db//
        SQLiteDatabase dba = this.getReadableDatabase();
        //wanting everything returned from table//
        //cursor holds all of the database rows//
        //The purpose of a cursor is to point to a single row of the result fetched by the query//
        //We load the row pointed by the cursor object//
        Cursor cursor = dba.rawQuery(query, null);
        //rawQuery() directly accepts an SQL select statement as input//
        //The method returns 'Cursor' object which points to one row of the query result//

        //cursor holds all of the databse rows//
        totalItems = cursor.getCount();

        cursor.close();

        return totalItems;
    }

    //select* from sleep_tracker where baby_name = " x baby_name "

    Cursor getSleepTrackerData(){
        String query = "SELECT * FROM  sleep_tracker where baby_name ='"+ x +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        if(db != null){
            c = db.rawQuery(query, null);
        }
        return c;
    }

    int totalCalories(){
        int cals = 0;

        SQLiteDatabase dba = this.getReadableDatabase();

        //getting sum of calories column//
        String query = "SELECT SUM( " + COLUMN_CAL + " ) " +
                "FROM " + TABLE_NAME5;

        Cursor cursor = dba.rawQuery(query, null);

        //if there is something there in the cursor then we are going to get the id at index 0//

        if (cursor.moveToFirst()) {
            cals = cursor.getInt(0);
        }

        //close rows and db//
        cursor.close();
        dba.close();

        return cals;

    }

    @SuppressLint("Range")
    public ArrayList<FoodClass> getFoods(){

        //clear our array we created before we add anything so nothing from previous query//
        foodList.clear();

        //reading from database//
        SQLiteDatabase db = this.getReadableDatabase();

        //create new string array of everything want to get from the db//
        Cursor cursor = db.query(TABLE_NAME5,
                new String[]{FOOD_ROW_ID, COLUMN_FOOD, COLUMN_CAL,
                        COLUMN_DATE}, null, null, null, null, COLUMN_DATE + " DESC ");

        //loop through cursor, now it has all items from db//
        //Cursor is an object that can iterate on the result rows of your query - cursor can move to each row//
        //move to first moves cursor to the first result//
        if (cursor.moveToFirst()) {
            do {

                //create instance of our food item//
                FoodClass food = new FoodClass();
                food.setFoodName(cursor.getString(cursor.getColumnIndex(COLUMN_FOOD)));
                food.setCalories(cursor.getInt(cursor.getColumnIndex(COLUMN_CAL)));
                food.setFoodId(cursor.getInt(cursor.getColumnIndex(FOOD_ROW_ID)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                @SuppressLint("Range") String date = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_DATE))).getTime());

                food.setRecordDate(date);

                foodList.add(food);

            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return foodList;
    }


    public Boolean checkTableEmpty(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from " + TABLE_NAME2, null);
        if (cursor.getCount() > 0) {
            Toast.makeText(context, "Table has data", Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(context, "Table is empty ", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    void updateTable(String row_id, String name, int age, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME2, name);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_GENDER, gender);

        long result = db.update(TABLE_NAME2, values, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to update description", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Description updated", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME2, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to delete description", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteFoodRow(int id) {

        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(TABLE_NAME5, FOOD_ROW_ID + " = ?",
                //convert id int into string - as putting into string array//
                new String[]{String.valueOf(id)});

        dba.close();
    }

    void deleteAllNames(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME2);
    }
}


