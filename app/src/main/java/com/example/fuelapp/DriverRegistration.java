package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DriverRegistration extends AppCompatActivity {

    //variables
    EditText name, phone, email, password, vRegNo, vehicleType;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        name = findViewById(R.id.driverNameInput);
        phone = findViewById(R.id.editTextTextPersonName2);
        email = findViewById(R.id.driverEmailInput);
        password = findViewById(R.id.passwordInput);
        vRegNo = findViewById(R.id.vehicleID);
        vehicleType = findViewById(R.id.vehicleType);

        registerButton = findViewById(R.id.driverRegBtn);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });
    }



    public void registerDriver(View view){
        try{
            if(TextUtils.isEmpty(name.getText().toString())){
                name.requestFocus();
                name.setError("Please enter your name");
            }else if(TextUtils.isEmpty(phone.getText().toString())){
                phone.requestFocus();
                phone.setError("Please enter a mobile number");
            }else if(TextUtils.isEmpty(email.getText().toString())){
                email.requestFocus();
                email.setError("Please enter an email address");
            }else if(TextUtils.isEmpty(password.getText().toString())){
                password.requestFocus();
                password.setError("Please enter a password");
            }else if(TextUtils.isEmpty(vRegNo.getText().toString())){
                vRegNo.requestFocus();
                vRegNo.setError("Please enter vehicle number");
            }else if(TextUtils.isEmpty(vehicleType.getText().toString())){
                vehicleType.requestFocus();
                vehicleType.setError("Please enter vehicle type");
            }
            else{

               //connection to backend for registration of a driver
                //set the userType as "Customer"

                emptyFilledData();

            }

        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"Internal Error occured refresh",Toast.LENGTH_SHORT).show();
        }
    }

    public void emptyFilledData(){
        name.setText("");
        phone.setText("");
        email.setText("");
        password.setText("");
        vRegNo.setText("");
        vehicleType.setText("");
    }


    }