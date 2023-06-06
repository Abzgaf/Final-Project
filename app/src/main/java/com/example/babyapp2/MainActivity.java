package com.example.babyapp2;

// Code for a basic login system, involving input validation and database interactions.
// It defines a main activity that has two text input fields for the username and password, and two buttons - one for signing up and one for logging in.

// When the user clicks on the sign-up button, the input values are first validated to check if they meet certain criteria like length and whitespace restrictions.
// If the input is valid, it is added to an SQLite database using the SQLdb class.

// When the user clicks on the login button, the input values are again validated, and if they are valid,
// the SQLdb class is used to check if the username exists in the database.
// If it does, the user is redirected to a new activity called "BabyList".



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private  int STORAGE_PERMISSION_CODE = 1;

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

        if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission was already granted!", Toast.LENGTH_SHORT).show();
        }else{
            requestStoragePermission();
        }
    }

    private void requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is required as we will store data on your device")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        }


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

    public void onRequestPermissions(){
        {
            
        }
    }




}