package com.structit.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.structit.location.service.LocationService;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = MyBroadcastReceiver.class.getSimpleName();

    private MainActivity mActivity;

    public MyBroadcastReceiver(MainActivity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() == LocationService.ACTION_LOCATION) {
            Log.i(LOG_TAG, "Intent received : " + intent.getAction());

            this.mActivity.displayLocation(
                    intent.getStringExtra(LocationService.ACTION_LOCATION_EXTRA_PROVIDER),
                    intent.getDoubleExtra(LocationService.ACTION_LOCATION_EXTRA_LATITUDE,
                            0.0),
                    intent.getDoubleExtra(LocationService.ACTION_LOCATION_EXTRA_LONGITUDE,
                            0.0));
        } else {
            Log.w(LOG_TAG, "Unknown intent received : " + intent.getAction());
        }
    }
}
