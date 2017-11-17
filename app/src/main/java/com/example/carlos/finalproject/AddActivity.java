package com.example.carlos.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Anja on 11/17/2017.
 */

public class AddActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);

        setTitle("Add Activity");
        //startDatabaseDemo();

//        shareButton = findViewById(R.id.shareButton);
//        shareButton.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), ShareActivity.class);
//                startActivityForResult(intent,1);
//            }
//        });
    }
}
