package com.example.babyapp2;
import static com.example.babyapp2.MainActivity.x;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Date;


public class SQLdb extends SQLiteOpenHelper {

    Connection con;
    String db_uname;
    String db_pass;
    String db_ip;
    String db_port;
    String database;

    public Connection connectionClass(){
        db_ip = "172.1.1.0";
        database = "BabyApp";
        db_uname = "sa";
        db_pass = "finalproject";
        db_port = "1433";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection connection = null;
        String ConnectionURL = null;

        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://"+ db_ip + ":" + db_port + ";"+ "databasename=" + database+ ";user="+ db_uname +";pass="+db_pass+";";
            connection = DriverManager.getConnection(ConnectionURL);
        }catch ( Exception ex){
            Log.e("error ", ex.getMessage());

        }
        return connection;

    }

    private static final String DATABASE_NAME = "BabyApp.db";
    private static final int DATABASE_VERSION = 1;


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


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
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
}


