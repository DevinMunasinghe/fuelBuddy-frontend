package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        fuelType = findViewById(R.id.arrivalTimeVal);
    }
}