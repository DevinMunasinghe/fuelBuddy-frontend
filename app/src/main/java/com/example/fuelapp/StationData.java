package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fuelapp.APIManager.Fuel;
import com.example.fuelapp.APIManager.FuelList;
import com.example.fuelapp.APIManager.RetrofitClient;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Data displaying of a particular selected Station
 *
 * @author Hasani Kariyawasam
 *
 * @method getFuelData(String stationId)
 * @returns fuel stock data
 */

public class StationData extends AppCompatActivity {

    //variables
    TextView stationName, stationAvailability, fuelType1 , fuelType2, fuelType3, fuelType4, arrivalTimeVal1,arrivalTimeVal2,arrivalTimeVal3,arrivalTimeVal4,finishTime1, finishTime2, finishTime3, finishTime4;
    TextView availability1,availability2,availability3,availability4;
    Button queueBtn,logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //id identification
        setContentView(R.layout.activity_station_data);
        stationName = findViewById(R.id.stationName1);
        stationAvailability=findViewById(R.id.stationAvailablity1);
        fuelType1=findViewById(R.id.fuelType1Name1);
        fuelType2=findViewById(R.id.fuelType2Name1);
        fuelType3=findViewById(R.id.fuelType3Name1);
        fuelType4=findViewById(R.id.fuelType4Name1);
        arrivalTimeVal1=findViewById(R.id.arrivalTimeVal);
        arrivalTimeVal2=findViewById(R.id.arrivalTime2Val);
        arrivalTimeVal3=findViewById(R.id.arrivalTime3Val);
        arrivalTimeVal4=findViewById(R.id.arrivalTime4Val);
        finishTime1=findViewById(R.id.finishTimeVal);
        finishTime2=findViewById(R.id.finishTime2Val);
        finishTime3=findViewById(R.id.finishTime3Val);
        finishTime4=findViewById(R.id.finishTime4Val);
        availability1 = findViewById(R.id.availabilityVal);
        availability2 = findViewById(R.id.availabilityVal2);
        availability3 = findViewById(R.id.availabilityVal3);
        availability4 = findViewById(R.id.availabilityVal4);


        queueBtn = findViewById(R.id.queueBTN);
        Intent intent = getIntent();

        stationName.setText(intent.getStringExtra("stationName"));
        stationAvailability.setText(intent.getStringExtra("stationAvailability"));
        String stationId = intent.getStringExtra("stationId");

        //triggering to retrieve fuel data of a specific station
        getFuelData(stationId);

        logoutBtn=findViewById(R.id.logoutbtn4);

        //trigger button click logout button
        logoutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StationData.this,MainActivity.class);
                startActivity(intent);

            }
        });

        //triggering the onclick for queue button
        queueBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StationData.this,QueueDetails.class);
                intent.putExtra("stationId",stationId);
                startActivity(intent);

            }
        });

    }


    // retrieving the fuel data from station and display
    public void getFuelData(String stationId){

        Call<FuelList> call = RetrofitClient.getInstance().getMyApi().getFulesInAStation(stationId);
        call.enqueue(new Callback<FuelList>() {
            @Override
            public void onResponse(Call<FuelList>  call, Response<FuelList>response) {

                Gson gson = new Gson();

                List<Fuel> list = response.body().getFuelList();
                fuelType1.setText(list.get(0).getType().toUpperCase());
                arrivalTimeVal1.setText(list.get(0).getArrival());
                finishTime1.setText(list.get(0).getComplete());
                availability1.setText(list.get(0).getStatus());

                fuelType2.setText(list.get(1).getType().toUpperCase());
                arrivalTimeVal2.setText(list.get(1).getArrival());
                finishTime2.setText(list.get(1).getComplete());
                availability2.setText(list.get(1).getStatus());

                fuelType3.setText(list.get(2).getType().toUpperCase());
                arrivalTimeVal3.setText(list.get(2).getArrival());
                finishTime3.setText(list.get(2).getComplete());
                availability3.setText(list.get(2).getStatus());

                fuelType4.setText(list.get(3).getType().toUpperCase());
                arrivalTimeVal4.setText(list.get(3).getArrival());
                finishTime4.setText(list.get(3).getComplete());
                availability4.setText(list.get(3).getStatus());

                if(availability1.getText().equals("Available")){
                    availability1.setTextColor(Color.parseColor("#00A300"));
                }else {
                    availability1.setTextColor(Color.parseColor("#FF0000"));
                }
                if(availability2.getText().equals("Available")){
                    availability2.setTextColor(Color.parseColor("#00A300"));
                }else {
                    availability2.setTextColor(Color.parseColor("#FF0000"));
                }
                if(availability3.getText().equals("Available")){
                    availability3.setTextColor(Color.parseColor("#00A300"));
                }else {
                    availability3.setTextColor(Color.parseColor("#FF0000"));
                }
                if(availability4.getText().equals("Available")){
                    availability4.setTextColor(Color.parseColor("#00A300"));
                }else {
                    availability4.setTextColor(Color.parseColor("#FF0000"));
                }

                System.out.println(gson.toJsonTree(response.body()));

            }

            @Override
            public void onFailure(Call<FuelList> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });
    }
}