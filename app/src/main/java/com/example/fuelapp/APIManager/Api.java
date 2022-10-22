package com.example.fuelapp.APIManager;





import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    String BASE_URL = "http://10.0.2.2:6000/api/v1/";

    @retrofit2.http.Headers("Content-type: application/json")
    @GET("users")
    Call<Object> viewUsers();

    @retrofit2.http.Headers("Content-type: application/json")
    @GET("stations")
    Call<StationLists> viewStations();

    @retrofit2.http.Headers("Content-type: application/json")
    @POST("users/register")
    Call<User> createUser(@Body User user);

    @retrofit2.http.Headers("Content-type: application/json")
    @POST("stations")
    Call<StationDet> createStation(@Body StationDet stationDet);

    @retrofit2.http.Headers("Content-type: application/json")
    @POST("users/login/")
    Call<Void> login(@Body LoginResult loginResult);


}
