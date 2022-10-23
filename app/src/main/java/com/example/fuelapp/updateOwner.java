package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class updateOwner extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText fuelType, arrivalDate , EndUpDate , Availability ;
    Button updateDetailsBtn;
    Spinner spinner, availability_spinner;
    String fuel, availability;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_owner);
        fuelType = findViewById(R.id.FuelTypeUpdateInput);
        arrivalDate = findViewById(R.id.arrivalUpdateInput);
        EndUpDate = findViewById(R.id.completeUpdateInput);
        Availability = findViewById(R.id.availabilityUpdateInput);
        spinner= (Spinner) findViewById(R.id.fuel_types);
        availability_spinner= (Spinner) findViewById(R.id.availability_types);



//        updateDetailsBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

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
}