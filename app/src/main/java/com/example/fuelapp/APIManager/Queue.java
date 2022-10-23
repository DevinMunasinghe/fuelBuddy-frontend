package com.example.fuelapp.APIManager;

import com.google.gson.annotations.SerializedName;

public class Queue {

    @SerializedName("stationId")
    private String stationId;

    @SerializedName("vehicleId")
    private String vehicleId;

    @SerializedName("vehicleType")
    private String vehicleType;

    @SerializedName("joined")
    private String joined;

    @SerializedName("exit")
    private String exit;

    @SerializedName("status")
    private String status;

    public Queue(String stationId, String vehicleId, String vehicleType, String joined, String status) {
        this.stationId = stationId;
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.joined = joined;
        this.status = status;
    }

    public Queue(String joined, String exit, String status) {
        this.joined = joined;
        this.exit = exit;
        this.status = status;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getJoined() {
        return joined;
    }

    public void setJoined(String joined) {
        this.joined = joined;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }
}
