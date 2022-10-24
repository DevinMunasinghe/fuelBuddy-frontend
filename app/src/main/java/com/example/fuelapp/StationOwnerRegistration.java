package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fuelapp.APIManager.Fuel;
import com.example.fuelapp.APIManager.RetrofitClient;
import com.example.fuelapp.APIManager.Station;
import com.example.fuelapp.APIManager.StationDet;
import com.example.fuelapp.APIManager.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationOwnerRegistration extends AppCompatActivity {

    //variables
    EditText name, phone, email, password, fuelStationName, fuelStationId,stationAddress;
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
        stationAddress =findViewById(R.id.stationAddress);

        registerButton = findViewById(R.id.ownerRegBtn);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                registerOwner(v);
                Toasty.Config.reset();
                Toasty.Config.getInstance().setGravity(200,0, 800).apply();
                //error Toast
                Toasty.error(getApplicationContext(), "Internal Error Occured", Toast.LENGTH_LONG, true).show();
                //Success Toast
                Toasty.success(getApplicationContext(), "Owner Details Registered Successfully!", Toast.LENGTH_LONG, true).show();
                //Warning Toast
                Toasty.warning(getApplicationContext(), "Owner Details Registered Successfully!", Toast.LENGTH_LONG, true).show();
                //Info Toast
                Toasty.info(getApplicationContext(), "Owner Details Registered Successfully!", Toast.LENGTH_LONG, true).show();
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
            }else if(TextUtils.isEmpty(stationAddress.getText().toString())){
                stationAddress.requestFocus();
                stationAddress.setError("Please enter station Address");
            } else if(TextUtils.isEmpty(fuelStationId.getText().toString())){
                fuelStationId.requestFocus();
                fuelStationId.setError("Please enter station Id");
            }
            else{

                //connection to backend for registration of a station Owner
                //register owner as a user + register the station as well
                //set the userType as "Owner"

                //creating station owner object
                String type = "owner";

                Station [] stations = new Station[1];

                Station stationData = new Station(fuelStationName.getText().toString(),
                        fuelStationId.getText().toString());
                stations[0] =stationData;

                User stationOwner = new User(name.getText().toString(),
                                            phone.getText().toString(),
                                            email.getText().toString(),
                                            password.getText().toString(),
                                            type,stations);

                //creating station object
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());


                String current = date + " " + currentTime;

                Fuel fuel1 = new Fuel("Diesel",current," ", "Available");
                Fuel fuel2 = new Fuel("Petrol 92",current," ", "Available");
                Fuel fuel3 = new Fuel("Petrol 95",current," ", "Available");
                Fuel fuel4 = new Fuel("Super Diesel",current," ", "Available");

               Fuel [] fuels = {fuel1,fuel2,fuel3,fuel4};


                StationDet stationDet = new StationDet(fuelStationId.getText().toString(),
                        fuelStationName.getText().toString(),
                        stationAddress.getText().toString(),
                        phone.getText().toString(),
                        email.getText().toString(),
                        fuels);


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
        stationAddress.setText("");
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
//                    Toast.makeText(getApplicationContext(),"Owner Details Registered Successfully",Toast.LENGTH_SHORT).show();
                    Toasty.success(getApplicationContext(), "Owner Details Registered Successfully!", Toast.LENGTH_LONG, true).show();
                }
            }

            @Override
            public void onFailure(Call<User>   call, Throwable t) {
//                Toast.makeText(getApplicationContext(),"Internal Error Occured",Toast.LENGTH_SHORT).show();
                Toasty.error(getApplicationContext(), "Internal Error Occured", Toast.LENGTH_LONG, true).show();
            }
        });
    }

    public void registerAStation(StationDet stationDet){

        Call<StationDet> call = RetrofitClient.getInstance().getMyApi().createStation(stationDet);

        call.enqueue(new Callback<StationDet>() {
            @Override
            public void onResponse(Call<StationDet>  call, Response<StationDet> response) {
                if(response.isSuccessful()){
//                    Toast.makeText(getApplicationContext(),"Station Created Successfully",Toast.LENGTH_SHORT).show();
                    Toasty.success(getApplicationContext(), "Station Created Successfully!", Toast.LENGTH_LONG, true).show();
                }
            }

            @Override
            public void onFailure(Call<StationDet>   call, Throwable t) {
//                Toast.makeText(getApplicationContext(),"Internal Error Occured",Toast.LENGTH_SHORT).show();
                Toasty.error(getApplicationContext(), "Internal Error Occured", Toast.LENGTH_LONG, true).show();
            }
        });
    }

}