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

import com.example.fuelapp.APIManager.Fuel;
import com.example.fuelapp.APIManager.RetrofitClient;
import com.example.fuelapp.APIManager.StationDet;
import com.example.fuelapp.APIManager.StationList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
               getStations();
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

    public  void getStations(){

        Call<StationList>  call = RetrofitClient.getInstance().getMyApi().viewStations();

        call.enqueue(new Callback<StationList>  () {
            @Override
            public void onResponse(Call<StationList>  call, Response<StationList>   response) {

                Gson gson = new Gson();

                List<StationDet> list = response.body().getStations();
                List<String> ids = new ArrayList<>();
                List<String> stationNames = new ArrayList<>();
                List<String> address = new ArrayList<>();
                List<String> phone = new ArrayList<>();
                List<String> email = new ArrayList<>();
                Fuel fuels[] = new Fuel[0];
                List<String> fuelTypes = new ArrayList<>();
                List<String> fuelArrivals = new ArrayList<>();
                List<String> fuelCompletes = new ArrayList<>();
                List<String> fuelStatus = new ArrayList<>();

                for(int i=0; i<list.size(); i++){
                    ids.add(list.get(i).getId());
                    stationNames.add(list.get(i).getName());
                    address.add(list.get(i).getAddress());
                    phone.add(list.get(i).getPhone());
                    email.add(list.get(i).getEmail());
                    fuels = list.get(i).getFuel();

                    for(int j=0; j<fuels.length; j++ ){
                        fuelTypes.add(fuels[j].getType());
                        fuelArrivals.add(fuels[j].getArrival());
                        fuelCompletes.add(fuels[j].getComplete());
                        fuelStatus.add(fuels[j].getStatus());
                    }

                }

                for(String f: fuelTypes){
                    System.out.println(gson.toJsonTree(f));

                }

                System.out.println(gson.toJsonTree(response.body()));

            }

            @Override
            public void onFailure(Call<StationList>   call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });



    }
    }