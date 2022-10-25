package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterPage extends AppCompatActivity {

    //variables
    Button driverButton;
    Button ownerButton;
    TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        //id identification
        driverButton = findViewById(R.id.driverBtn);
        ownerButton = findViewById(R.id.stationOwnerBtn);
        loginLink = findViewById(R.id.loginPageLink);

        //triggering the driver registering button
        driverButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterPage.this,DriverRegistration.class);
                startActivity(intent);

            }
        });

        //triggering the station owner registering button
        ownerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterPage.this,StationOwnerRegistration.class);
                startActivity(intent);

            }
        });

        //triggering the button to link login
        loginLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterPage.this,MainActivity.class);
                startActivity(intent);

            }
        });

    }
}