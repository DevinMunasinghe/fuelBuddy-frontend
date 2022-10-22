package com.example.fuelapp.APIManager;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FuelList {

    @SerializedName("data")
    private List<Fuel> fuelList;

    public FuelList(List<Fuel> fuelList) {
        this.fuelList = fuelList;
    }

    public List<Fuel> getFuelList() {
        return fuelList;
    }

    public void setFuelList(List<Fuel> fuelList) {
        this.fuelList = fuelList;
    }
}
