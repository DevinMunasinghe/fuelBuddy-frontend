package com.example.fuelapp.SQLiteDatabase;

import com.example.fuelapp.APIManager.Station;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {

    private String userName;

    private String userPhone;

    private String userMail;

    private String userType;

    private String userId;

    private String userToken;

    public Users() {
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserMail() {
        return userMail;
    }

    public String getUserType() {
        return userType;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
