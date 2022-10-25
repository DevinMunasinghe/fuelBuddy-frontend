package com.example.fuelapp.APIManager;

import com.google.gson.annotations.SerializedName;

import java.util.List;
/**
 * StationList data management data model
 *
 * @author Hasani Kariyawasam
 *
 * @variables stations - list created of Station objects
 *
 * @constructor StatList(List<Station> stations)
 *
 * and @getters and @setters for the variables managed
 */

public class StatList {

    @SerializedName("data")
    private List<Station> stations;

    public StatList(List<Station> stations) {
        this.stations = stations;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }
}
