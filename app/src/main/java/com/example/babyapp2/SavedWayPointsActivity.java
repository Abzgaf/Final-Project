package com.example.babyapp2;

// code which displays a list of saved locations (waypoints).

// The code then gets a reference to the application context and retrieves a list of saved locations using GPS2 class.
// Finally, it sets an adapter for the ListView that displays the saved locations using a simple one-line layout provided by the Android framework.

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class SavedWayPointsActivity extends AppCompatActivity {
    ListView WayPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_way_points);

        WayPoints = findViewById(R.id.WayPoints);

        GPS2 gps = (GPS2)getApplicationContext();
        List<Location> savedLocations = gps.getLocations();

        WayPoints.setAdapter(new ArrayAdapter<Location>(this, android.R.layout.simple_list_item_1 ,savedLocations));
    }
}