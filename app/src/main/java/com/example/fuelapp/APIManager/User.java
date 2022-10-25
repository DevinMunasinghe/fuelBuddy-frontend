package com.example.fuelapp.APIManager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * User data management data model
 *
 * @author Hasani Kariyawasam
 *
 * @variables name,phone,email,password,type, vehicleType,vehicleId, station
 *
 * @constructor User(String name, String phone, String email, String password, String type, String vehicleType, String vehicleId) to manage the details of drivers
 * @constructor User(String name, String phone, String email, String password, String type, Station[] station) to manage the details of station owners
 *
 * and @getters and @setters for the variables managed
 */

public class User {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("vehicleType")
    @Expose
    private String vehicleType;

    @SerializedName("vehicleId")
    @Expose
    private String vehicleId;

    @SerializedName("station")
    @Expose
    private Station[] station;

    public User(String name, String phone, String email, String password, String type, String vehicleType, String vehicleId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.type = type;
        this.vehicleType = vehicleType;
        this.vehicleId = vehicleId;
    }

    public User(String name, String phone, String email, String password, String type, Station[] station) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.type = type;
        this.station = station;
    }


    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public Station[] getStation() {
        return station;
    }


}
