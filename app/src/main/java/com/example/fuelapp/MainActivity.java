package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuelapp.APIManager.LoginResult;
import com.example.fuelapp.APIManager.RetrofitClient;
import com.example.fuelapp.APIManager.StationDet;
import com.example.fuelapp.APIManager.User;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView newUser;
    EditText email, password;
    Button loginBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newUser=findViewById(R.id.registerPageLink);
        email=findViewById(R.id.emailInput);
        password=findViewById(R.id.loginPasswordInput);
        loginBtn=findViewById(R.id.loginButton);

        newUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterPage.class);
                startActivity(intent);

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //creating a loginObject
                LoginResult loginObject = new LoginResult(email.getText().toString(),
                                                            password.getText().toString());

                loginUser(loginObject);
            }
        });


    }

    public void loginUser(LoginResult loginResult){

        Call<Void> call = RetrofitClient.getInstance().getMyApi().login(loginResult);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void>  call, Response<Void> response) {
                Gson gson  = new Gson();
                System.out.println(gson.toJsonTree(gson.toJsonTree(response.body())));

                    Toast.makeText(getApplicationContext(),"Login Successfull",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Void>  call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Internal Error Occured",Toast.LENGTH_SHORT).show();
            }
        });
    }


}