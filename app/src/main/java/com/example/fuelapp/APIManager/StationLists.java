package com.example.fuelapp.APIManager;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
