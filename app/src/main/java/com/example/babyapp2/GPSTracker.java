package com.example.babyapp2;

// GPSTracker class that tracks the user's GPS location and allows them to save waypoints.

// WayPoint_btn.setOnClickListener: This sets a click listener for the "WayPoint" button.
// When the button is clicked, the current location is added to the saved locations list.

// ListOfWP_btn.setOnClickListener: This sets a click listener for the "ListOfWP" button.
// When the button is clicked, the SavedWayPointsActivity is started, which displays the list of saved waypoints.

// Map_btn.setOnClickListener: This sets a click listener for the "Map" button.
// When the button is clicked, the MapsActivity is started, which displays the map with the user's current location and saved waypoints.

// gps_switch.setOnClickListener: This sets a click listener for the GPS switch.
// When the switch is toggled, it changes the location request priority between using GPS sensors (high accuracy) , using towers
// and Wi-Fi sensors (balanced power accuracy).

// updateLoc_switch.setOnClickListener: This sets a click listener for the update location switch.
// When the switch is toggled, it either starts or stops location updates.

// onRequestPermissionsResult: This method is called when permissions are requested, and handles the response.
// If the user grants the location permission, it updates the GPS, otherwise, it shows a toast message and finishes the activity.

// updateGPS: This method updates the location and starts the GPS tracking.
// It checks if the user has granted location permission and then requests the user's last known location and calls updateUIValues with the retrieved location.

// updateUIValues: This method takes a Location object as input and updates the UI with the location's address.
// It also updates the waypoint count displayed in the UI with the number of saved locations.

// The purpose of this Android app is to track the user's location using GPS, save waypoints, and display the saved waypoints in a list and on a map.
// The user can also choose between using GPS sensors or towers and Wi-Fi sensors for location tracking.

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class GPSTracker extends AppCompatActivity {

    public static final int DEFAULT_UPDATE_INTERVAL = 30;
    public static final int FAST_UPDATE_INTERVAL = 5;
    TextView sensor_type_txt, update_type_txt, shownLoc_txt, wayPointCount_txt;
    Switch gps_switch, updateLoc_switch;
    Button WayPoint_btn, ListOfWP_btn, Map_btn;

    LocationRequest locationRequest;
    LocationCallback locationCallback;
    Location currentLocation;
    List<Location> savedLocations;

    //Google API
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpstracker);

        sensor_type_txt = findViewById(R.id.sensor_type_txt);
        update_type_txt = findViewById(R.id.update_type_txt);
        shownLoc_txt = findViewById(R.id.shownLoc_txt);
        wayPointCount_txt = findViewById(R.id.wayPointCount_txt);
        gps_switch = findViewById(R.id.gps_switch);
        updateLoc_switch = findViewById(R.id.updateLoc_switch);
        WayPoint_btn = findViewById(R.id.WayPoint_btn);
        ListOfWP_btn = findViewById(R.id.ListOfWP_btn);
        Map_btn = findViewById(R.id.Map_btn);

        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                updateUIValues(locationResult.getLastLocation());
            }
        };

        WayPoint_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPS2 gps = (GPS2)getApplicationContext();
                savedLocations = gps.getLocations();
                savedLocations.add(currentLocation);
            }
        });

        ListOfWP_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GPSTracker.this, SavedWayPointsActivity.class);
                startActivity(intent);
            }
        });

        Map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GPSTracker.this, MapsActivity.class);
                startActivity(intent);
            }
        });


        gps_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (gps_switch.isChecked()) {
                  locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                  sensor_type_txt.setText("Using GPS sensors");
              }else{
                  locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                  sensor_type_txt.setText("Using Towers + WIFI sensors");
              }

            }
        });
        updateLoc_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(updateLoc_switch.isChecked()){
                    startLocationUpdates();
                }else{
                    stopLocationUpdates();
                }
            }
        });
        updateGPS();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    private void stopLocationUpdates() {

        update_type_txt.setText("Location is NOT being tracked");
        sensor_type_txt.setText("Not available");
        shownLoc_txt.setText("Not available");
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {

        update_type_txt.setText("Location is being tracked");
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        updateGPS();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void updateGPS(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location!= null) {
                        updateUIValues(location);
                        currentLocation = location;
                    }
                }
            });
        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    private void updateUIValues(Location location){

        Geocoder geocoder = new Geocoder(GPSTracker.this);
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            shownLoc_txt.setText(addresses.get(0).getAddressLine(0));
        } catch (Exception e) {
            shownLoc_txt.setText("Not Working");
        }

        GPS2 gps = (GPS2)getApplicationContext();
        savedLocations = gps.getLocations();
        wayPointCount_txt.setText(String.valueOf(savedLocations.size()));


    }

}