package com.structit.location.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class LocationService extends Service {
    private static final String LOG_TAG = LocationService.class.getSimpleName();

    public static final String ACTION_LOCATION = "com.structit.location.LOCATION";
    public static final String ACTION_LOCATION_EXTRA_PROVIDER = "LOCATION_PROVIDER";
    public static final String ACTION_LOCATION_EXTRA_LATITUDE = "LOCATION_LATITUDE";
    public static final String ACTION_LOCATION_EXTRA_LONGITUDE = "LOCATION_LONGITUDE";

    @Override
    public int onStartCommand (Intent intent, int flags, int startId)
    {
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager)
                this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationServiceListener locationListener = new LocationServiceListener(this);

        // Register the listener with the Location Manager to receive location updates
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    10, 1, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    10, 1, locationListener);
        } catch(SecurityException ex) {
            Log.e(LOG_TAG, ex.getMessage());
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void notifyLocation(Location location) {
        Intent intent = new Intent(ACTION_LOCATION);
        intent.putExtra(ACTION_LOCATION_EXTRA_PROVIDER, location.getProvider());
        intent.putExtra(ACTION_LOCATION_EXTRA_LATITUDE, location.getLatitude());
        intent.putExtra(ACTION_LOCATION_EXTRA_LONGITUDE, location.getLongitude());

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}

