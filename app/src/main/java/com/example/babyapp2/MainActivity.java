package com.example.babyapp2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username_input;
    EditText pass_input;
    Button login_btn;
    Button login;

    public static String x;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username_input = findViewById(R.id.username_input);
        pass_input = findViewById(R.id.pass_input);
        login_btn = findViewById(R.id.login_btn);
        login = findViewById(R.id.login);


        login_btn.setOnClickListener(new View.OnClickListener() {
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
                Toast.makeText(MainActivity.this, x, Toast.LENGTH_SHORT).show();
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
        //dfsfdfint a = Integer.parseInt(val);
        String checkPassword =
                "(.*[0-9])";         //at least 1 digit

        if(val.isEmpty()){
            pass_input.setError("Field is empty");
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