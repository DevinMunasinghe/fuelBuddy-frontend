package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class QueueDetails extends AppCompatActivity {

    TextView totalVehicleCount,totalCarCount, totalBusCount, totalThreeWheelCount , totalBikeCount,waitingTime,vehicleToPump;
    Button joinToQueueBtn , exitBeforePumpBtn , exitAfterPumpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_details);


        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        System.out.println(">>>>"+date);
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        System.out.println("time>>>>"+currentTime);

        totalVehicleCount = findViewById(R.id.vehicleCount);
        totalCarCount = findViewById(R.id.totCarCount);
        totalBusCount = findViewById(R.id.totBusCount);
        totalBikeCount = findViewById(R.id.totBikeCount);
        totalThreeWheelCount = findViewById(R.id.totThreewheelCount);
        waitingTime = findViewById(R.id.waitingCount);
        vehicleToPump = findViewById(R.id.nextJoinCount);

        joinToQueueBtn = findViewById(R.id.joiToQueueBtn);
        exitBeforePumpBtn = findViewById(R.id.exitBeforePumpBtn);
        exitAfterPumpBtn = findViewById(R.id.exitAfterPumpBtn);

        joinToQueueBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });

        exitBeforePumpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });

        exitAfterPumpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });


    }
}