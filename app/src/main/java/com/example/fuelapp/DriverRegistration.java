package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fuelapp.APIManager.Fuel;
import com.example.fuelapp.APIManager.RetrofitClient;
import com.example.fuelapp.APIManager.StationDet;
import com.example.fuelapp.APIManager.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DriverRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //variables
    EditText name, phone, email, password, vRegNo, vehicleType;
    Button registerButton;
    Spinner vehicle_type_spinner;

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
//        vehicleType = findViewById(R.id.vehicleType);

        vehicle_type_spinner= (Spinner) findViewById(R.id.vehicle_type_spinner);

        registerButton = findViewById(R.id.driverRegBtn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vehicle_type_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        vehicle_type_spinner.setOnItemSelectedListener(this);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                registerDriver(v);
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
                String type = "customer";
                User driver = new User(name.getText().toString(),
                        phone.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString(),
                        type,
                        vehicleType.getText().toString(),
                        vRegNo.getText().toString());


                registerVehicleDriver(driver);

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

    public void registerVehicleDriver(User user){

        System.out.println(user);
        Call<User> call = RetrofitClient.getInstance().getMyApi().createUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User>  call, Response<User> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Driver Registered Successfully",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Registration Unsuccessful",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User>   call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Internal Error Occured",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String choice = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}