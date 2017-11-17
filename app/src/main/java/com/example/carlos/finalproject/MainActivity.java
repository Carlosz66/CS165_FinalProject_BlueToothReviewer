package com.example.carlos.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startDatabaseDemo();

        shareButton = findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShareActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    private void startDatabaseDemo() {
        DatabaseHelper myDbHelper = new DatabaseHelper(this);

        try {
            myDbHelper.openDataBase();
            Toast.makeText(this, "Connected successfully!", Toast.LENGTH_SHORT).show();
        }

        catch (SQLException sqle) {
            Toast.makeText(this, "Cannot connect to the database.", Toast.LENGTH_LONG).show();
            throw sqle;
        }

        String query = "select LocationName, ActivityName, StartTime from ScheduledActivity";
        Cursor cursor = myDbHelper.getReadableDatabase().rawQuery(query, null);

        String message = "";

        if (cursor.moveToFirst()){
            do {
                String locationName = cursor.getString(cursor.getColumnIndex("LocationName"));
                String activityName = cursor.getString(cursor.getColumnIndex("ActivityName"));
                String startTime = cursor.getString(cursor.getColumnIndex("StartTime"));

                message = activityName + " at " + locationName + " starting at " + startTime;
            }

            while (cursor.moveToNext());
        }

        cursor.close();

        Toast.makeText(this, "Database demo: " + message, Toast.LENGTH_LONG).show();
    }
}
