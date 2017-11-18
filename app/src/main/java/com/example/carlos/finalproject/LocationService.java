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
import android.widget.Toast;


public class LocationService extends Service {


    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Handler mHandler;
    private Runnable onRequestLocation;
    private int cnt;

    private Location LastKnownLocation, curLocation;

    public LocationService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("onStartCommand","on");

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                curLocation = location;
                String str = location.toString();
                updateDB(location);
                Log.d("cur location",str);
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

                Toast.makeText(getApplication(),"the"+cnt+"time update location",Toast.LENGTH_SHORT).show();
                cnt++;
                if (ActivityCompat.checkSelfPermission(getApplication(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getApplication(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    // grantPermission
                    return;
                }
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
                mHandler.postDelayed(onRequestLocation, DateUtils.SECOND_IN_MILLIS);
            }
        };

        mHandler.post(onRequestLocation);



        return START_STICKY;
    }


    private void updateDB(Location location){

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
