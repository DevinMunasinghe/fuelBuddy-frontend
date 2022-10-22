package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterPage extends AppCompatActivity {

    Button driverButton;
    Button ownerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        driverButton = findViewById(R.id.driverBtn);
        ownerButton = findViewById(R.id.stationOwnerBtn);

    }
}