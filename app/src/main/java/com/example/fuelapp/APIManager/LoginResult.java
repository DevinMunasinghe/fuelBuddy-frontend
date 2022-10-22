package com.example.fuelapp.APIManager;

import com.google.gson.annotations.SerializedName;

public class LoginResult {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public LoginResult(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
