package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
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

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Management of the driver details registration
 *
 * @author Hasani Kariyawasam
 *
 * @method registerDriver() , emptyFilledData(), registerVehicleDriver()
 *
 */


public class DriverRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //variables
    EditText name, phone, email, password, vRegNo;
    Button registerButton;
    Spinner vehicle_type_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        //id identification
        name = findViewById(R.id.driverNameInput);
        phone = findViewById(R.id.editTextTextPersonName2);
        email = findViewById(R.id.driverEmailInput);
        password = findViewById(R.id.passwordInput);
        vRegNo = findViewById(R.id.vehicleID);
        vehicle_type_spinner= (Spinner) findViewById(R.id.vehicle_type_spinner);
        registerButton = findViewById(R.id.driverRegBtn);

        //spinner handling
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vehicle_type_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        vehicle_type_spinner.setAdapter(adapter);
        vehicle_type_spinner.setOnItemSelectedListener(this);

        //register button trigger
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                registerDriver(v);}
        });


    }


    //registration of a driver
    public void registerDriver(View view){

        Toasty.Config.reset();
        Toasty.Config.getInstance().setGravity(Gravity.CENTER_VERTICAL|Gravity.START,240, -580).apply();

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
            }
            else{

               //connection to backend for registration of a driver
                String type = "customer";
                User driver = new User(name.getText().toString(),
                        phone.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString(),
                        type,
                        vehicle_type_spinner.getSelectedItem().toString(),
                        vRegNo.getText().toString());


                registerVehicleDriver(driver);

                emptyFilledData();

            }

        }catch(Exception e){
            Toasty.warning(getApplicationContext(), "Cannot proceed please recheck details", Toast.LENGTH_LONG, true).show();
        }
    }

    public void emptyFilledData(){
        name.setText("");
        phone.setText("");
        email.setText("");
        password.setText("");
        vRegNo.setText("");
    }

    //registering a user as a driver through web API call.
    public void registerVehicleDriver(User user){

        Toasty.Config.reset();
        Toasty.Config.getInstance().setGravity(Gravity.CENTER_VERTICAL|Gravity.START,240, -580).apply();

        Call<User> call = RetrofitClient.getInstance().getMyApi().createUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User>  call, Response<User> response) {

                if(response.isSuccessful()){
                    Toasty.success(getApplicationContext(), "Driver Registered Successfully!", Toast.LENGTH_LONG, true).show();
                }else {
                    Toasty.error(getApplicationContext(), "Registration Unsuccessful", Toast.LENGTH_LONG, true).show();
                }
            }

            @Override
            public void onFailure(Call<User>   call, Throwable t) {
                Toasty.error(getApplicationContext(), "Internal Error Occurred", Toast.LENGTH_LONG, true).show();
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