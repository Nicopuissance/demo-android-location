package com.structit.location;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.structit.location.service.LocationService;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private MyBroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, getString(R.string.debug_create_msg));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        Log.i(LOG_TAG, getString(R.string.debug_start_msg));

        super.onStart();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        } else {
            Log.i(LOG_TAG, "ACCESS_COARSE_LOCATION authorization granted");
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else {
            Log.i(LOG_TAG, "ACCESS_FINE_LOCATION authorization granted");
        }

        this.mBroadcastReceiver = new MyBroadcastReceiver(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver,
                new IntentFilter(LocationService.ACTION_LOCATION));

        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
    }

    public void displayLocation(String provider, double lat, double lng) {
        TextView providerTextView = (TextView) findViewById(R.id.location_provider_id);
        providerTextView.setText(provider);

        TextView latitudeTextView = (TextView) findViewById(R.id.location_latitude_id);
        latitudeTextView.setText("" + lat);

        TextView longitudeTextView = (TextView) findViewById(R.id.location_longitude_id);
        longitudeTextView.setText("" + lng);
    }
}
