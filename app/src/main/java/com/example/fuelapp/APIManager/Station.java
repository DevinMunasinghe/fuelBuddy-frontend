package com.example.fuelapp.APIManager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Station data management data model during the user registration
 *
 * @author Hasani Kariyawasam
 *
 * @variables name,id
 *
 * @constructor Station(String name, String id)
 *
 * and @getters and @setters for the variables managed
 */

public class Station {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private String id;

    public Station(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
