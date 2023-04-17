package com.example.babyapp2;

import androidx.annotation.NonNull;
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
    TextView tv_lat, tv_lon, tv_sensor, tv_updates, tv_address, tv_WPCount;
    Switch sw_gps, sw_locationsupdates;
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

        tv_lat = findViewById(R.id.tv_lat);
        tv_lon = findViewById(R.id.tv_lon);
        tv_sensor = findViewById(R.id.tv_sensor);
        tv_updates = findViewById(R.id.tv_updates);
        tv_address = findViewById(R.id.tv_address);
        tv_WPCount = findViewById(R.id.tv_WPCount);
        sw_gps = findViewById(R.id.sw_gps);
        sw_locationsupdates = findViewById(R.id.sw_locationsupdates);
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


        sw_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (sw_gps.isChecked()) {
                  locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                  tv_sensor.setText("Using GPS sensors");
              }else{
                  locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                  tv_sensor.setText("Using Towers + WIFI sensors");
              }

            }
        });
        sw_locationsupdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw_locationsupdates.isChecked()){
                    startLocationUpdates();
                }else{
                    stopLocationUpdates();
                }
            }
        });
        updateGPS();

    }

    private void stopLocationUpdates() {

        tv_updates.setText("Location is NOT being tracked");
        tv_lat.setText("Not tracking location");
        tv_lon.setText("Not tracking location");
        tv_sensor.setText("Not available");
        tv_address.setText("Not available");
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {

        tv_updates.setText("Location is being tracked");
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
        tv_lat.setText(String.valueOf(location.getLatitude()));
        tv_lon.setText(String.valueOf(location.getLongitude()));

        Geocoder geocoder = new Geocoder(GPSTracker.this);
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            tv_address.setText(addresses.get(0).getAddressLine(0));
        } catch (Exception e) {
            tv_address.setText("Not Working");
        }

        GPS2 gps = (GPS2)getApplicationContext();
        savedLocations = gps.getLocations();
        tv_WPCount.setText(String.valueOf(savedLocations.size()));


    }

}