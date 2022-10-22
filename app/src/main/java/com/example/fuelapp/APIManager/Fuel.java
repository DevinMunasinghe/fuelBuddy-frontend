package com.example.fuelapp.APIManager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fuel {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("arrival")
    @Expose
    private String arrival;
    @SerializedName("complete")
    @Expose
    private String complete;
    @SerializedName("status")
    @Expose
    private String status;

    public Fuel(String type, String arrival, String complete, String status) {
        this.type = type;
        this.arrival = arrival;
        this.complete = complete;
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
