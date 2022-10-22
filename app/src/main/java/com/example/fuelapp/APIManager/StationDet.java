package com.example.fuelapp.APIManager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StationDet {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("phone")
    private String phone;

    @SerializedName("email")
    private String email;

    @SerializedName("fuel")
    private Fuel[] fuel;

    public StationDet(String id, String name, String address, String phone, String email, Fuel[] fuel) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.fuel = fuel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Fuel[] getFuel() {
        return fuel;
    }

    public void setFuel(Fuel[] fuel) {
        this.fuel = fuel;
    }
}
