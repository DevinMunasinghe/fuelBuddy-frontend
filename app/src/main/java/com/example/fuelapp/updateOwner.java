package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class updateOwner extends AppCompatActivity {

    EditText fuelType, arrivalDate , EndUpDate , Availability ;
    Button updateDetailsBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_owner);
        fuelType = findViewById(R.id.FuelTypeUpdateInput);
        arrivalDate = findViewById(R.id.arrivalUpdateInput);
        EndUpDate = findViewById(R.id.completeUpdateInput);
        Availability = findViewById(R.id.availabilityUpdateInput);

        updateDetailsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

    }
}