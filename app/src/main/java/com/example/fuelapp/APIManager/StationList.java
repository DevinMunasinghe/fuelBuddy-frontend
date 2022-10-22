package com.example.fuelapp.APIManager;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StationList {

    @SerializedName("data")
    private List<StationDet> stations;

    public StationList(List<StationDet> stations) {
        this.stations = stations;
    }

    public List<StationDet> getStations() {
        return stations;
    }
}
