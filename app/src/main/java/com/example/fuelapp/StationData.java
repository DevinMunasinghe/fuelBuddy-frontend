package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StationData extends AppCompatActivity {

    TextView stationName, stationAvailability, fuelType1 , fuelType2, fuelType3, fuelType4, arrivalTimeVal1,arrivalTimeVal2,arrivalTimeVal3,arrivalTimeVal4,finishTime1, finishTime2, finishTime3, finishType4;
    Button queueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_data);
        stationName = findViewById(R.id.stationName1);
        stationAvailability=findViewById(R.id.stationAvailablity1);
        fuelType1=findViewById(R.id.fuelType1Name1);
        fuelType2=findViewById(R.id.fuelType2Name1);
        fuelType3=findViewById(R.id.fuelType3Name1);
        fuelType4=findViewById(R.id.fuelType4Name1);
        arrivalTimeVal1=findViewById(R.id.arrivalTimeVal);
        arrivalTimeVal2=findViewById(R.id.arrivalTime2Val);
        arrivalTimeVal3=findViewById(R.id.arrivalTime3Val);
        arrivalTimeVal4=findViewById(R.id.arrivalTime4Val);
        finishTime1=findViewById(R.id.finishTimeVal);
        finishTime2=findViewById(R.id.finishTime2Val);
        finishTime3=findViewById(R.id.finishTime3Val);
        finishType4=findViewById(R.id.arrivalTime4Val);

        queueBtn = findViewById(R.id.queueBTN);
        Intent intent = getIntent();

        stationName.setText(intent.getStringExtra("stationName"));
        stationAvailability.setText(intent.getStringExtra("stationAvailability"));

        queueBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StationData.this,QueueDetails.class);
                startActivity(intent);

            }
        });
    }
}