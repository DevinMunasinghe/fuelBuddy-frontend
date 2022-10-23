package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterPage extends AppCompatActivity {

    Button driverButton;
    Button ownerButton;

    TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        driverButton = findViewById(R.id.driverBtn);
        ownerButton = findViewById(R.id.stationOwnerBtn);
        loginLink = findViewById(R.id.loginPageLink);

        driverButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterPage.this,DriverRegistration.class);
                startActivity(intent);

            }
        });

        ownerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterPage.this,StationOwnerRegistration.class);
                startActivity(intent);

            }
        });

        loginLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterPage.this,MainActivity.class);
                startActivity(intent);

            }
        });

    }
}