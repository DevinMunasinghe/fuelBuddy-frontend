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
/**
 * Data displaying of a queue of a specific station
 *
 * @author Hasani Kariyawasam
 *
 * @method getQueueLengthsByVehicleType(vehicleType, id)
 * @method getQueueLength(String stationId)
 * @method joinToQueue(Queue queue)
 * @method checkJoinedVehicle(String vehicleId)
 * @method exitQueueBeforePump(String stationId,String vehicleId, Queue queue)
 * @method exitQueueBeforePump(String stationId,String vehicleId, Queue queue)
 * @method exitQueueAfterPump(String stationId,String vehicleId, Queue queue)
 *
 *
 */


public class QueueDetails extends AppCompatActivity {

    //variables
    TextView totalVehicleCount,totalCarCount, totalBusCount, totalThreeWheelCount , totalBikeCount,waitingTime,vehicleToPump;
    Button joinToQueueBtn , exitBeforePumpBtn , exitAfterPumpBtn;
    Button logoutBtn;

    String vehicleCount = "0";
    String vehicleJoined = "Haven't joined to a queue yet";
    String stationId,vehicleId,vehicleType,current;
    String name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_details);

        //obtaining passed intent
        Intent intent =  getIntent();
        stationId = intent.getStringExtra("stationId");
        name =intent.getStringExtra("stationName");
        phone=intent.getStringExtra("stationAvailability");

        Globaldata sharedData = Globaldata.getInstance();
        vehicleId = sharedData.getNotification_indexOne();
        vehicleType = sharedData.getNotification_indexTwo();

        //setting the date
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        //id identification
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
        logoutBtn=findViewById(R.id.logoutbtn3);

        //triggering the methods to retrieve vehicle count by type
        getQueueLengthsByVehicleType(stationId,"Car");
        getQueueLengthsByVehicleType(stationId,"Bus");
        getQueueLengthsByVehicleType(stationId,"Three-Wheel");
        getQueueLengthsByVehicleType(stationId,"Bike");

        //triggering the queue length
        getQueueLength(stationId);

        //triggering the total wait time
        getQueueWaitTime(stationId);

        current = date + " " + currentTime;

        //trigger button click logout button
        logoutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QueueDetails.this,MainActivity.class);
                startActivity(intent);

            }
        });


        //triggering the onclick for join to queue
        joinToQueueBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String idStation = stationId;
                String vehicle = vehicleId;
                String vehicleT = vehicleType;
                String joined = current;
                String status = "Joined to the queue";

                //creating a queue object
                Queue queue = new Queue(idStation,vehicle,vehicleT,joined,status);
                checkJoinedVehicle(vehicle);
                System.out.println("VEHICLE JOINED"+vehicleJoined);



            }
        });

        //triggering the onclick for exit before pump button
        exitBeforePumpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String idStation = stationId;
                String vehicle = vehicleId;
                String joined = current;
                String exit = current;
                String status = "Exit before Pump";

                //creating a queue object
                Queue queue = new Queue(joined,exit,status);
                exitQueueBeforePump(idStation,vehicle,queue);
                Intent intent=new Intent(QueueDetails.this,StationData.class);
                intent.putExtra("stationId",stationId);
                intent.putExtra("stationName",name);
                intent.putExtra("stationAvailability",phone);
                startActivity(intent);
            }
        });

        //triggering the onclick for exit after pump button
        exitAfterPumpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String idStation = stationId;
                String vehicle = vehicleId;
                String joined = current;
                String exit = current;
                String status = "Exit After Pump";

                //creating a queue object
                Queue queue = new Queue(joined,exit,status);
                exitQueueAfterPump(idStation,vehicle,queue);
                Intent intent=new Intent(QueueDetails.this,StationData.class);
                intent.putExtra("stationId",stationId);
                intent.putExtra("stationName",name);
                intent.putExtra("stationAvailability",phone);
                startActivity(intent);
            }
        });

    }

    //retrieve the length of the queue by vehicle type
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
                vehicleCount = vehicleCount.substring(0,message.length()-2);

                    if(vehicleType.equals("Car")){
                        totalCarCount.setText(vehicleCount);
                    }else if(vehicleType.equals("Bus")){
                        totalBusCount.setText(vehicleCount);
                    }else if(vehicleType.equals("Three-Wheel")){
                        totalThreeWheelCount.setText(vehicleCount);
                    }else if(vehicleType.equals("Bike")){
                        totalBikeCount.setText(vehicleCount);
                    }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });

    }

    //retrieving total queue length
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


                totalVehicleCount.setText(message.substring(0,message.length()-2));

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });

    }

    //retrieving the total wait time
    public void getQueueWaitTime(String stationId){

        Call<Object> call = RetrofitClient.getInstance().getMyApi().getQueueWaitingTime(stationId);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object>  call, Response<Object> response) {

                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonElement element = gson.toJsonTree(response.body());
                JsonObject rootObject = element.getAsJsonObject();
                System.out.println(rootObject.toString().equals("{}"));
                if(rootObject.toString().equals("{}")){
                    String message = "0";
                    waitingTime.setText(message);
                }else{
                    String message = rootObject.get("data").getAsString();
                    waitingTime.setText(message.substring(0,message.length()-4));
                }




            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });

    }

    //executing the join to queue with web API
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

                System.out.println(message);
                Toast.makeText(getApplicationContext(),"Joined To Queue",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });

    }

    //executing function to check weather vehicle is already in queue with web API
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
                vehicleJoined = message;

                String idStation = stationId;
                String vehicle = vehicleId;
                String vehicleT = vehicleType;
                String joined = current;
                String status = "Joined to the queue";

                //creating a queue object
                Queue queue = new Queue(idStation,vehicle,vehicleT,joined,status);

                if(vehicleJoined.equals("Haven't joined to a queue yet")){
                    joinToQueue(queue);
                    Intent intent=new Intent(QueueDetails.this,StationData.class);
                    intent.putExtra("stationId",stationId);
                    intent.putExtra("stationName",name);
                    intent.putExtra("stationAvailability",phone);
                    startActivity(intent);
                }else if(vehicleJoined.charAt(0) == '0')
                {
                    Toast.makeText(getApplicationContext(),"Vehicle is already in a queue "+ vehicleJoined,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Vehicle is already in a queue "+ vehicleJoined,Toast.LENGTH_SHORT).show();
                }

                System.out.println("MESSAGE" +message );
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });

    }

    //executing function to exit queue before pump with web API
    public void exitQueueBeforePump(String stationId,String vehicleId, Queue queue){

        Call<Object> call = RetrofitClient.getInstance().getMyApi().updateJoinedQueue(stationId,vehicleId,queue);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object>  call, Response<Object> response) {

                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonElement element = gson.toJsonTree(response.body());
                JsonObject rootObject = element.getAsJsonObject();
                String message = rootObject.get("message").getAsString();
                System.out.println(message);
                if(message.equals("Details Updated")){
                    Toast.makeText(getApplicationContext(),"Exiting Queue Before Pump",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });

    }

    //executing function to exit queue after pump with web API
    public void exitQueueAfterPump(String stationId,String vehicleId, Queue queue){

        Call<Object> call = RetrofitClient.getInstance().getMyApi().updateJoinedQueue(stationId,vehicleId,queue);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object>  call, Response<Object> response) {

                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonElement element = gson.toJsonTree(response.body());
                JsonObject rootObject = element.getAsJsonObject();
                String message = rootObject.get("message").getAsString();
                System.out.println(message);
                if(message.equals("Details Updated")){
                    Toast.makeText(getApplicationContext(),"Exiting Queue After Pump",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });

    }


}