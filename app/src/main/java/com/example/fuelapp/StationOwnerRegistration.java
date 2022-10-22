package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fuelapp.APIManager.Fuel;
import com.example.fuelapp.APIManager.RetrofitClient;
import com.example.fuelapp.APIManager.StationDet;
import com.example.fuelapp.APIManager.StationList;
import com.example.fuelapp.APIManager.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationOwnerRegistration extends AppCompatActivity {

    //variables
    EditText name, phone, email, password, fuelStationName, fuelStationId;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_owner_registration);

        name = findViewById(R.id.ownerNameInput);
        phone = findViewById(R.id.ownerPhoneNumber);
        email = findViewById(R.id.ownerEmail);
        password = findViewById(R.id.ownerPassword);
        fuelStationName = findViewById(R.id.stationName);
        fuelStationId = findViewById(R.id.stationId);

        registerButton = findViewById(R.id.ownerRegBtn);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                registerOwner(v);
            }
        });
    }

    public void registerOwner(View view){
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
            }else if(TextUtils.isEmpty(fuelStationName.getText().toString())){
                fuelStationName.requestFocus();
                fuelStationName.setError("Please enter station Name");
            }else if(TextUtils.isEmpty(fuelStationId.getText().toString())){
                fuelStationId.requestFocus();
                fuelStationId.setError("Please enter station Id");
            }
            else{

                //connection to backend for registration of a station Owner
                //register owner as a user + register the station as well
                //set the userType as "Owner"

                //creating station owner object
                String type = "owner";
                User stationOwner = new User(name.getText().toString(),
                                            phone.getText().toString(),
                                            email.getText().toString(),
                                            password.getText().toString(),
                                            type,
                                            fuelStationName.getText().toString(),
                                            fuelStationId.getText().toString());

                //creating station object
                String address = "NO 1569/KL,Kotte";
                Fuel fuel[] = new Fuel[0];
                StationDet stationDet = new StationDet(fuelStationId.getText().toString(),
                        fuelStationName.getText().toString(),
                        address,
                        phone.getText().toString(),
                        email.getText().toString(),
                        fuel);


                registerStationOwner(stationOwner,stationDet);

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
        fuelStationName.setText("");
        fuelStationId.setText("");
    }

    public void registerStationOwner(User user, StationDet stationDet){

        System.out.println(user);
        Call<User> call = RetrofitClient.getInstance().getMyApi().createUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User>  call, Response<User> response) {
                if(response.isSuccessful()){
                    registerAStation(stationDet);
                    Toast.makeText(getApplicationContext(),"Owner Details Registered Successfully",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User>   call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Internal Error Occured",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void registerAStation(StationDet stationDet){

        Call<StationDet> call = RetrofitClient.getInstance().getMyApi().createStation(stationDet);

        call.enqueue(new Callback<StationDet>() {
            @Override
            public void onResponse(Call<StationDet>  call, Response<StationDet> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Station Created Successfully",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StationDet>   call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Internal Error Occured",Toast.LENGTH_SHORT).show();
            }
        });
    }

}