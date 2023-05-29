package com.example.babyapp2;

// GPS2 which extends the Application class.

// The getLocations() method returns the value of the locations variable. The setLocations() method sets the value of the locations variable.
// The getInstance() method returns the singleton instance of GPS2.
// The onCreate() method initializes the singleton instance and creates an empty ArrayList to store the locations.

import android.app.Application;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class GPS2 extends Application {
    private static GPS2 singleton;

    private List<Location> locations;

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public GPS2 getInstance() {
        return singleton;
    }

    public void onCreate() {
        super.onCreate();
        singleton = this;
        locations = new ArrayList<>();
    }
}
