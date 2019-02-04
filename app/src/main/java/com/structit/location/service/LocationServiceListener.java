package com.structit.location.service;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class LocationServiceListener implements LocationListener {
    private static final String LOG_TAG = LocationServiceListener.class.getSimpleName();

    private LocationService mService;

    public LocationServiceListener(LocationService service) {
        this.mService = service;
    }

    public void onLocationChanged(Location location) {
        Log.i(LOG_TAG, "provider: " + location.getProvider());
        Log.i(LOG_TAG, "latitude: " + location.getLatitude());
        Log.i(LOG_TAG, "longitude: " + location.getLongitude());

        this.mService.notifyLocation(location);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(LOG_TAG, "new state");
    }

    public void onProviderEnabled(String provider) {
        Log.i(LOG_TAG, "provider enable");
    }

    public void onProviderDisabled(String provider) {
        Log.i(LOG_TAG, "provider disable");
    }
}
