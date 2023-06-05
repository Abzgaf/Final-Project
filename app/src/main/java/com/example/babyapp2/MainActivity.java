package com.example.babyapp2;

// Code for a basic login system, involving input validation and database interactions.
// It defines a main activity that has two text input fields for the username and password, and two buttons - one for signing up and one for logging in.

// When the user clicks on the sign-up button, the input values are first validated to check if they meet certain criteria like length and whitespace restrictions.
// If the input is valid, it is added to an SQLite database using the SQLdb class.

// When the user clicks on the login button, the input values are again validated, and if they are valid,
// the SQLdb class is used to check if the username exists in the database.
// If it does, the user is redirected to a new activity called "BabyList".



import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText username_input;
    EditText pass_input;
    Button signIn_btn;
    Button login;

    public static String x;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        username_input = findViewById(R.id.username_input);
        pass_input = findViewById(R.id.pass_input);
        signIn_btn = findViewById(R.id.signIn_btn);
        login = findViewById(R.id.login);


        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validatePassword()) {
                    return;
                }
                SQLdb db = new SQLdb(MainActivity.this);
                db.addData(username_input.getText().toString().trim(),
                        pass_input.getText().toString().trim());
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x = username_input.getText().toString().trim();
                if (!validateUsername() | !validatePassword()) {
                    return;
                }
                SQLdb db = new SQLdb(MainActivity.this);
                if(db.checkusername(username_input.getText().toString().trim())){
                    Intent intent = new Intent(MainActivity.this, BabyList.class);
                    startActivity(intent);

                }
            }
        });
    }
    private boolean validateUsername() {
        String val = username_input.getText().toString().trim();
        String checkSpaces = "\\A\\w{1,20}\\z";

        if(val.isEmpty()){
            username_input.setError("Field can not be empty");
            return false;
        }else if (val.length() > 20){
            username_input.setError("Username is too long!");
            return false;
        }else if(val.length() < 5) {
            username_input.setError("Username is too short!");
            return false;
        }else if (!val.matches(checkSpaces)){
            username_input.setError("No white spaces allowed!");
            return false;
        }else {
            username_input.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String val = pass_input.getText().toString().trim();
        String checkPassword =
                "(.*[4-9])";         //at least 4 digits

        if(val.isEmpty()){
            pass_input.setError("Field is empty");
            return false;
        }
        else if (val.length() < 3) {
            username_input.setError("Username is too short!");
            return false;
        }
        else if (val.length() > 10) {
            username_input.setError("Username is too long!");
            return false;
        }
        else if (!val.matches(checkPassword)){
            pass_input.setError("Password should only be a pin");
            return false;
        } else{
            pass_input.setError(null);
            return true;
        }
    }




}