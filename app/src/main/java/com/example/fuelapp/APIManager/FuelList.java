package com.example.fuelapp.APIManager;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * FuelList data management data model
 *
 * @author Hasani Kariyawasam
 *
 * @variables fuelList - list created of Fuel objects
 *
 * @constructor FuelList(List<Fuel> fuelList)
 *
 * and @getters and @setters for the variables managed
 */

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
