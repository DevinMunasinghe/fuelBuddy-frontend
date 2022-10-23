package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.Calendar;

public class updateOwner extends AppCompatActivity implements AdapterView.OnItemSelectedListener , View.OnClickListener {

    EditText fuelType  ;
    Button updateDetailsBtn;
    Spinner spinner, availability_spinner;
    String fuel, availability;
    TextView arrivalTime,  endUpTime,arrivalDate , EndUpDate ;

    Button btnArrivalDatePicker, btnArrivalTimePicker,btnCompleteDatePicker,btnCompleteTimePicker;
    private int mYear, mMonth, mDay, mHour, mMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_owner);
        fuelType = findViewById(R.id.FuelTypeUpdateInput);

        spinner= (Spinner) findViewById(R.id.fuel_types);
        availability_spinner= (Spinner) findViewById(R.id.availability_types);

        btnArrivalDatePicker=(Button)findViewById(R.id.btn_arrival_date);
        btnArrivalTimePicker=(Button)findViewById(R.id.btn_arrival_time);
        btnCompleteDatePicker=(Button)findViewById(R.id.btn_endup_date);
        btnCompleteTimePicker= (Button)findViewById(R.id.btn_end_up_time);

        arrivalDate = findViewById(R.id.arrivalUpdateInput);
        arrivalTime =findViewById(R.id.arrivalUpdateTimeInput);
        EndUpDate = findViewById(R.id.completeUpdateInput);
        endUpTime = findViewById(R.id.completeUpdateTimeInput);

        btnArrivalDatePicker.setOnClickListener(this);
        btnArrivalTimePicker.setOnClickListener(this);
        btnCompleteDatePicker.setOnClickListener(this);
        btnCompleteTimePicker.setOnClickListener(this);



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
//        System.out.println( availability_spinner.setOnItemSelectedListener(this));

        fuel = spinner.getSelectedItem().toString();
        availability = availability_spinner.getSelectedItem().toString();

        System.out.println("fuel>>>>>>"+fuel);
        System.out.println("avai>>>>>>"+availability);



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        System.out.println("selected2323232>>"+id);


        String choice = adapterView.getItemAtPosition(position).toString();
        System.out.println("selected>>"+choice);
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

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

                            arrivalDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

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

                            EndUpDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

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
                    }, mHour, mMinute, false);
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
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

}