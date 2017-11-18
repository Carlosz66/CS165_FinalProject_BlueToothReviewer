package com.example.carlos.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    Button shareButton, addActivityButton, startTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shareButton = findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShareActivity.class);
                startActivityForResult(intent,1);
            }
        });

        addActivityButton = findViewById(R.id.addActivityButton);
        addActivityButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(intent,2);
            }
        });

        startTracking = findViewById(R.id.startTrackingButton);
        startTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent trackingService = new Intent(MainActivity.this, LocationService.class);
                startService(trackingService);
                Log.d("start tracking","main");
            }
        });
    }
}
