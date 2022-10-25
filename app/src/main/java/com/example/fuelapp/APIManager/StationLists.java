package com.example.fuelapp.APIManager;

import com.google.gson.annotations.SerializedName;

import java.util.List;
/**
 * StationList data management data model
 *
 * @author Hasani Kariyawasam
 *
 * @variables fuelList - list created of StationDet objects
 *
 * @constructor StationLists(List<StationDet> stations)
 *
 * and @getters and @setters for the variables managed
 */


public class StationLists {

    @SerializedName("data")
    private List<StationDet> stations;

    public StationLists(List<StationDet> stations) {
        this.stations = stations;
    }

    public List<StationDet> getStations() {
        return stations;
    }
}
