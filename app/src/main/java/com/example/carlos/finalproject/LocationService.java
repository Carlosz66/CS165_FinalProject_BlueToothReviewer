package com.example.carlos.finalproject;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.text.format.DateUtils;
import android.util.Log;


public class LocationService extends Service {


    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Handler mHandler;
    private Runnable onRequestLocation;
    private int cnt;

    public LocationService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("onStartCommand","on");

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mLocationManager.removeUpdates(mLocationListener);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}

            @Override
            public void onProviderEnabled(String s) {}

            @Override
            public void onProviderDisabled(String s) {}
        };
        cnt = 0;
        mHandler = new Handler();
        onRequestLocation = new Runnable() {
            @Override
            public void run() {
                // Ask for a location
                cnt++;
                Log.d("update every minute",cnt+"");
                if (ActivityCompat.checkSelfPermission(getApplication(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getApplication(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    // grantPermission
                    return;
                }
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
                mHandler.postDelayed(onRequestLocation, DateUtils.MINUTE_IN_MILLIS);
            }
        };


        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
