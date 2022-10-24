package com.example.fuelapp.APIManager;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
