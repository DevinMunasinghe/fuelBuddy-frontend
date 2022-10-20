package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StationData extends AppCompatActivity {

    TextView stationName, stationAvailability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_data);
        stationName = findViewById(R.id.stationName1);
        stationAvailability=findViewById(R.id.stationAvailablity1);
        Intent intent = getIntent();

        stationName.setText(intent.getStringExtra("stationName"));
        stationAvailability.setText(intent.getStringExtra("stationAvailability"));
    }
}