package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuelapp.APIManager.Globaldata;
import com.example.fuelapp.APIManager.LoginResult;
import com.example.fuelapp.APIManager.RetrofitClient;
import com.example.fuelapp.APIManager.StationDet;
import com.example.fuelapp.APIManager.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Management user login
 *
 * @author Hasani Kariyawasam
 *
 * @method loginUser(LoginResult Result) , @param result
 * @method emptyFilledData(),
 *
 */

public class MainActivity extends AppCompatActivity {

    //variables
    TextView newUser;
    EditText email, password;
    Button loginBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //id identification
        newUser=findViewById(R.id.registerPageLink);
        email=findViewById(R.id.emailInput);
        password=findViewById(R.id.loginPasswordInput);
        loginBtn=findViewById(R.id.loginButton);

        //trigger button click for a new user registration
        newUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterPage.class);
                startActivity(intent);

            }
        });

        //trigger button click for login
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toasty.Config.reset();
                Toasty.Config.getInstance().setGravity(Gravity.CENTER_VERTICAL|Gravity.START,240, -220).apply();

                try{
                    if(TextUtils.isEmpty(email.getText().toString())){
                        email.requestFocus();
                        email.setError("Please enter valid email");
                    }else if(TextUtils.isEmpty(password.getText().toString())){
                        password.requestFocus();
                        password.setError("Please enter valid password");
                    }else{

                        //creating a loginObject
                        LoginResult loginObject = new LoginResult(email.getText().toString(),
                                password.getText().toString());

                        //executing login function
                        loginUser(loginObject);
                        emptyFilledData();
                    }
                }catch (Exception e){
                   Toasty.warning(getApplicationContext(), "Cannot proceed please try after some time", Toast.LENGTH_LONG, true).show();
                }


            }
        });


    }

    //login function connected with the web API
    public void loginUser(LoginResult loginResult){

        Toasty.Config.reset();
        Toasty.Config.getInstance().setGravity(Gravity.CENTER_VERTICAL|Gravity.START,240, -220).apply();

        Globaldata sharedData = Globaldata.getInstance();
        Call<Object> call = RetrofitClient.getInstance().getMyApi().login(loginResult);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object>  call, Response<Object> response) {
                Gson gson  = new Gson();

                JsonParser parser = new JsonParser();
                JsonElement element = gson.toJsonTree(response.body());
                JsonObject rootObject = element.getAsJsonObject();

                if(!rootObject.isJsonNull()){
                    Toasty.success(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG, true).show();
                    if(rootObject.get("type").getAsString().equals("customer")){
                        String vehicleId = rootObject.get("vehicleId").getAsString();
                        String vehicleType = rootObject.get("vehicleType").getAsString();
                        System.out.println(vehicleId);
                        Intent intent=new Intent(MainActivity.this,StationList.class);
                        intent.putExtra("vehicleId",vehicleId);
                        sharedData.setNotification_indexOne(vehicleId);
                        sharedData.setNotification_indexTwo(vehicleType);
                        startActivity(intent);

                    }else{
                        String name = rootObject.get("name").getAsString();
                        String phone = rootObject.get("phone").getAsString();
                        String email = rootObject.get("email").getAsString();
                        Intent intent=new Intent(MainActivity.this,ownerStationList.class);
                        intent.putExtra("name",name);
                        intent.putExtra("phone",phone);
                        intent.putExtra("email",email);
                        startActivity(intent);
                    }
                }else{
                    Toasty.warning(getApplicationContext(), "Recheck your credentials", Toast.LENGTH_LONG, true).show();
                }
            }

            @Override
            public void onFailure(Call<Object>  call, Throwable t) {
                Toasty.error(getApplicationContext(), "Internal Error Occurred", Toast.LENGTH_LONG, true).show();
            }
        });
    }

    //To empty the input fields
    public void emptyFilledData(){
        email.setText("");
        password.setText("");
    }

}