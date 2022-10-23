package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuelapp.APIManager.Fuel;
import com.example.fuelapp.APIManager.FuelList;
import com.example.fuelapp.APIManager.Globaldata;
import com.example.fuelapp.APIManager.Queue;
import com.example.fuelapp.APIManager.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QueueDetails extends AppCompatActivity {

    TextView totalVehicleCount,totalCarCount, totalBusCount, totalThreeWheelCount , totalBikeCount,waitingTime,vehicleToPump;
    Button joinToQueueBtn , exitBeforePumpBtn , exitAfterPumpBtn;

    String vehicleCount = "0";
    String vehicleJoined = "Haven't joined to a queue yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_details);

        Intent intent =  new Intent();
        String stationId = intent.getStringExtra("stationId");

        Globaldata sharedData = Globaldata.getInstance();
        String vehicleId = sharedData.getNotification_indexOne();
        String vehicleType = sharedData.getNotification_indexTwo();



        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//        System.out.println(">>>>"+date);
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
//        System.out.println("time>>>>"+currentTime);

        totalVehicleCount = findViewById(R.id.totVehicleValue);
        totalCarCount = findViewById(R.id.totCarCount);
        totalBusCount = findViewById(R.id.totBusCount);
        totalBikeCount = findViewById(R.id.totBikeCount);
        totalThreeWheelCount = findViewById(R.id.totThreewheelCount);
        waitingTime = findViewById(R.id.waitingCount);
        vehicleToPump = findViewById(R.id.nextJoinCount);

        joinToQueueBtn = findViewById(R.id.joiToQueueBtn);
        exitBeforePumpBtn = findViewById(R.id.exitBeforePumpBtn);
        exitAfterPumpBtn = findViewById(R.id.exitAfterPumpBtn);

        //change the hard codeded station id to dynamic variable
        getQueueLengthsByVehicleType("0001","Car");
        getQueueLengthsByVehicleType("0001","Bus");
        getQueueLengthsByVehicleType("0001","Three-Wheel");
        getQueueLengthsByVehicleType("0001","Bike");

        getQueueLength("0001");
        getQueueWaitTime("0001");

        String current = date + " " + currentTime;



        joinToQueueBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String idStation = "0001";
                String vehicle = "KGT 0774";
                String vehicleT = "Car";
                String joined = current;
                String status = "Joined to the queue";

                //creating a queue object
                Queue queue = new Queue(idStation,vehicle,vehicleT,joined,status);
                checkJoinedVehicle(vehicle);

                if(vehicleJoined.equals("Haven't joined to a queue yet")){
                    joinToQueue(queue);
                }else
                {
                    Toast.makeText(getApplicationContext(),"Vehicle is already in a queue "+ vehicleJoined,Toast.LENGTH_SHORT).show();
                }



            }
        });

        exitBeforePumpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });

        exitAfterPumpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });

    }

    public void getQueueLengthsByVehicleType(String stationId, String vehicleType){

        Call<Object> call = RetrofitClient.getInstance().getMyApi().getQueueLengthByVehicle(stationId,vehicleType);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object>  call, Response<Object> response) {

                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonElement element = gson.toJsonTree(response.body());
                JsonObject rootObject = element.getAsJsonObject();
                String message = rootObject.get("data").getAsString();

//                System.out.println(message);
                vehicleCount = message;

                if(vehicleType.equals("Car")){
                    totalCarCount.setText(message);
                }else if(vehicleType.equals("Bus")){
                    totalBusCount.setText(message);
                }else if(vehicleType.equals("Three-Wheel")){
                    totalThreeWheelCount.setText(message);
                }else if(vehicleType.equals("Bike")){
                    totalBikeCount.setText(message);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });

    }

    public void getQueueLength(String stationId){

        Call<Object> call = RetrofitClient.getInstance().getMyApi().getQueueVehicleCount(stationId);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object>  call, Response<Object> response) {

                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonElement element = gson.toJsonTree(response.body());
                JsonObject rootObject = element.getAsJsonObject();
                String message = rootObject.get("data").getAsString();

                totalVehicleCount.setText(message);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });

    }

    public void getQueueWaitTime(String stationId){

        Call<Object> call = RetrofitClient.getInstance().getMyApi().getQueueWaitingTime(stationId);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object>  call, Response<Object> response) {

                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonElement element = gson.toJsonTree(response.body());
                JsonObject rootObject = element.getAsJsonObject();
                String message = rootObject.get("data").getAsString();

                waitingTime.setText(message);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });

    }

    public void joinToQueue(Queue queue){

        Call<Object> call = RetrofitClient.getInstance().getMyApi().joinAQueue(queue);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object>  call, Response<Object> response) {

                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonElement element = gson.toJsonTree(response.body());
                JsonObject rootObject = element.getAsJsonObject();
                String message = rootObject.get("data").getAsString();

//                System.out.println(message);
                Toast.makeText(getApplicationContext(),"Joined To Queue",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });

    }

    public void checkJoinedVehicle(String vehicleId){

        Call<Object> call = RetrofitClient.getInstance().getMyApi().checkJoinedVehicle(vehicleId);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object>  call, Response<Object> response) {

                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonElement element = gson.toJsonTree(response.body());
                JsonObject rootObject = element.getAsJsonObject();
                String message = rootObject.get("data").getAsString();
//                System.out.println(message);
                vehicleJoined = message;

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });

    }
}