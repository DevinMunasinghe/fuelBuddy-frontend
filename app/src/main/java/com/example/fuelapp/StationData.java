package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.fuelapp.APIManager.Fuel;
import com.example.fuelapp.APIManager.FuelList;
import com.example.fuelapp.APIManager.RetrofitClient;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationData extends AppCompatActivity {

    TextView stationName, stationAvailability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_data);
        stationName = findViewById(R.id.stationName1);
        stationAvailability=findViewById(R.id.stationAvailablity1);
        Intent intent = getIntent();

        stationName.setText(intent.getStringExtra("stationName"));
        stationAvailability.setText(intent.getStringExtra("stationAvailability"));
        String stationId = intent.getStringExtra("stationId");

        getFuelData(stationId);

    }

    public void getFuelData(String stationId){

        Call<FuelList> call = RetrofitClient.getInstance().getMyApi().getFulesInAStation("0001");
        call.enqueue(new Callback<FuelList>() {
            @Override
            public void onResponse(Call<FuelList>  call, Response<FuelList>response) {

                Gson gson = new Gson();

                List<Fuel> list = response.body().getFuelList();
                for(Fuel fuel: list){
                    System.out.println(fuel.getType());
                }


                System.out.println(gson.toJsonTree(response.body()));

            }

            @Override
            public void onFailure(Call<FuelList> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }
}