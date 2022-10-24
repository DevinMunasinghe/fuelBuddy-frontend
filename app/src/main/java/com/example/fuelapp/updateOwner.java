package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.fuelapp.APIManager.Fuel;
import com.example.fuelapp.APIManager.FuelList;
import com.example.fuelapp.APIManager.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class updateOwner extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {

    //variables
    TextView arrivalTime,  endUpTime,arrivalDate , EndUpDate ;
    Button updateDetailsBtn;
    Spinner spinner, availability_spinner;
    String id;


    List<Fuel> fuels = new ArrayList<>();

    String arrivalZero, arrivalTimeZero, completeZero, completeTimeZero,avialabilityZero = "unavailable";
    String arrivalOne, arrivalTimeOne, completeOne, completeTimeOne, avialabilityOne  = "unavailable";
    String arrivalThree, arrivalTimeThree,completeThree, completeTimeThree,avialabilityThree  = "unavailable";
    String arrivalFour, arrivalTimeFour, completeFour, completeTimeFour,avialabilityFour  = "unavailable";

    Button btnArrivalDatePicker, btnArrivalTimePicker,btnCompleteDatePicker,btnCompleteTimePicker;
    private int mYear, mMonth, mDay, mHour, mMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_owner);

        //id identification
        arrivalDate = findViewById(R.id.arrivalUpdateInput);
        arrivalTime =findViewById(R.id.arrivalUpdateTimeInput);
        EndUpDate = findViewById(R.id.completeUpdateInput);
        endUpTime = findViewById(R.id.completeUpdateTimeInput);

        spinner= (Spinner) findViewById(R.id.fuel_types);
        availability_spinner= (Spinner) findViewById(R.id.availability_types);
        updateDetailsBtn = findViewById(R.id.updateStationBtn);

       //date picker variables
        btnArrivalDatePicker=(Button)findViewById(R.id.btn_arrival_date);
        btnArrivalTimePicker=(Button)findViewById(R.id.btn_arrival_time);
        btnCompleteDatePicker=(Button)findViewById(R.id.btn_endup_date);
        btnCompleteTimePicker= (Button)findViewById(R.id.btn_end_up_time);

        //spinner management
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.fuel_type_array, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> availabilityAdapter = ArrayAdapter.createFromResource(this,
                R.array.availability_array, android.R.layout.simple_spinner_item);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        availabilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        availability_spinner.setAdapter(availabilityAdapter);

        spinner.setOnItemSelectedListener(this);
        availability_spinner.setOnItemSelectedListener(this);


        btnArrivalDatePicker.setOnClickListener(this);
        btnArrivalTimePicker.setOnClickListener(this);
        btnCompleteDatePicker.setOnClickListener(this);
        btnCompleteTimePicker.setOnClickListener(this);

        id = getIntent().getStringExtra("stationId");

        //trigger update button
        updateDetailsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String stationId = id;
                String fuelType = spinner.getSelectedItem().toString();
                //creating update fuel object
                String arrivalDateS = arrivalDate.getText().toString();
                String arrivalTimeS = arrivalTime.getText().toString();
                String EndDate = EndUpDate.getText().toString();
                String EndDateTime= endUpTime.getText().toString();

                String arrivalDateTime = arrivalDateS+ " " + arrivalTimeS;
                String completeDateTime = EndDate+ " " + EndDateTime+":00";

                System.out.println(arrivalDateTime);
                System.out.println(completeDateTime);


                String availability = availability_spinner.getSelectedItem().toString();

                Fuel fuel = new Fuel(arrivalDateTime,completeDateTime,availability);
                //triggering the update of fuel stock
                updateStock(stationId,fuelType,fuel);

            }
        });

        //trigger get One station fuel stocks update data
        getData(id);
    }


    public void getData(String id){
        Call<FuelList> call = RetrofitClient.getInstance().getMyApi().getFulesInAStation(id);

        call.enqueue(new Callback<FuelList>() {
            @Override
            public void onResponse(Call<FuelList>  call, Response<FuelList> response) {
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    List<Fuel> list = response.body().getFuelList();
                    accessArrayList(list);
                }
            }

            @Override
            public void onFailure(Call<FuelList> call, Throwable t) {
                Toasty.info(getApplicationContext(), "Network Error Unable to load data!", Toast.LENGTH_LONG, true).show();
            }
        });
    }

    //access data within the response of getData() method
    public void accessArrayList(List<Fuel> list){
        fuels = list;

        arrivalDate.setText(fuels.get(0).getArrival().substring(0,11));
        arrivalTime.setText(fuels.get(0).getArrival().substring(11));
        if(fuels.get(0).getComplete().length() != 0 && fuels.get(0).getComplete().length() != 1){
            EndUpDate.setText(fuels.get(0).getComplete().substring(0,11));
            endUpTime.setText(fuels.get(0).getComplete().substring(11));
        }else{
            EndUpDate.setText(fuels.get(0).getComplete());
        }

        if(fuels.get(0).getStatus().equals("available") ||fuels.get(0).getStatus().equals("Available") ){
            availability_spinner.setSelection(0);
        }else{
            availability_spinner.setSelection(1);
        }

        arrivalZero= fuels.get(0).getArrival().substring(0,11);
        arrivalTimeZero = fuels.get(0).getArrival().substring(11);
        if(fuels.get(0).getComplete().length() != 0){
            completeZero =fuels.get(0).getComplete().substring(0,11);
            completeTimeZero =fuels.get(0).getComplete().substring(11);
        }else{
            completeZero =fuels.get(0).getComplete();
            completeTimeZero =fuels.get(0).getComplete();
        }

        if(fuels.get(0).getStatus().equals("available") || fuels.get(0).getStatus().equals("Available"));
        {
            avialabilityZero = "available";
        }


        arrivalOne= fuels.get(1).getArrival().substring(0,11);
        arrivalTimeOne = fuels.get(1).getArrival().substring(11);
        System.out.println(fuels.get(1).getComplete().length());
        if(fuels.get(1).getComplete().length() != 0 && fuels.get(1).getComplete().length() != 1){
            completeOne =fuels.get(1).getComplete().substring(0,11);
            completeTimeOne =fuels.get(1).getComplete().substring(11);
        }else{
            completeOne =fuels.get(1).getComplete();
            completeTimeOne =fuels.get(1).getComplete();
        }

        if(fuels.get(1).getStatus().equals("available") && fuels.get(1).getStatus().equals("Available"));
        {
            avialabilityOne = "available";
        }


        arrivalThree= fuels.get(2).getArrival().substring(0,11);
        arrivalTimeThree = fuels.get(2).getArrival().substring(11);
        if(fuels.get(2).getComplete().length() != 0 && fuels.get(2).getComplete().length() != 1){
            completeThree =fuels.get(2).getComplete().substring(0,11);
            completeTimeThree =fuels.get(2).getComplete().substring(11);
        }else{
            completeThree =fuels.get(2).getComplete();
            completeTimeThree =fuels.get(2).getComplete();
        }

        if(fuels.get(2).getStatus().equals("available") || fuels.get(2).getStatus().equals("Available"));
        {
            avialabilityThree = "available";
        }


        arrivalFour= fuels.get(3).getArrival().substring(0,11);
        arrivalTimeFour = fuels.get(3).getArrival().substring(11);
        if(fuels.get(3).getComplete().length() != 0 && fuels.get(3).getComplete().length() != 1){
            completeFour =fuels.get(3).getComplete().substring(0,11);
            completeTimeFour =fuels.get(3).getComplete().substring(11);
        }else{
            completeFour =fuels.get(3).getComplete();
            completeTimeFour =fuels.get(3).getComplete();
        }

        if(fuels.get(3).getStatus().equals("available") || fuels.get(3).getStatus().equals("Available"));
        {
            avialabilityFour = "available";
        }

    }


    //management of a spinner selection
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        String choice = adapterView.getItemAtPosition(position).toString();

        if(choice.equals("Petrol 92")){
            arrivalDate.setText(arrivalOne);
            arrivalTime.setText(arrivalTimeOne);
            EndUpDate.setText(completeOne);
            endUpTime.setText(completeTimeOne);

//            if(avialabilityOne == "available"){
//                availability_spinner.setSelection(0);
//            }
        }else if(choice.equals("Petrol 95")){
            arrivalDate.setText(arrivalThree);
            arrivalTime.setText(arrivalTimeThree);
            EndUpDate.setText(completeThree);
            endUpTime.setText(completeTimeThree);

//            if(avialabilityThree == "available" ){
//                availability_spinner.setSelection(0);
//            }
        }else if(choice.equals("Super Diesel")){
            arrivalDate.setText(arrivalFour);
            arrivalTime.setText(arrivalTimeFour);
            EndUpDate.setText(completeFour);
            endUpTime.setText(completeTimeFour);
//            if(avialabilityFour == "available"){
//                availability_spinner.setSelection(0);
//            }
        }else if(choice.equals("Diesel")){
            arrivalDate.setText(arrivalZero);
            arrivalTime.setText(arrivalTimeZero);
            EndUpDate.setText(completeZero);
            endUpTime.setText(completeTimeZero);
//            if(avialabilityZero == "available"){
//                System.out.println("DIESELLLLLL");
//                availability_spinner.setSelection(0);
//            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    //update the fuel stock data with web API
    public void updateStock(String id, String fuelType, Fuel fuel){

        Toasty.Config.reset();
        Toasty.Config.getInstance().setGravity(Gravity.CENTER_VERTICAL|Gravity.START,240, -630).apply();

        Call<Object> call = RetrofitClient.getInstance().getMyApi().updateFuelStock(id,fuelType,fuel);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object>  call, Response<Object> response) {
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    System.out.println(gson.toJsonTree(response.body()));
                    Toasty.success(getApplicationContext(), "Owner Details Registered Successfully!", Toast.LENGTH_LONG, true).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Internal Error Occurred", Toast.LENGTH_LONG, true).show();
            }
        });
    }

    //date and time picker management
    @Override
    public void onClick(View v) {
        System.out.println("onclick>>"+v);
        if (v == btnArrivalDatePicker) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            arrivalDate.setText(year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnCompleteDatePicker) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            EndUpDate.setText( year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnArrivalTimePicker) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            arrivalTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        }
        if (v == btnCompleteTimePicker) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            endUpTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        }
    }
}