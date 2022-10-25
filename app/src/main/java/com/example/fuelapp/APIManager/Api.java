package com.example.fuelapp.APIManager;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


/**
 * API calls to the backend implemented
 * <p>BASE URL =http://10.0.2.2:6000/api/v1/
 *
 * @author Hasani Kariyawasam
 *
 * @params String stationId, String vehicle Id
 *
 */

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
    Call<Object> login(@Body LoginResult loginResult);

    @retrofit2.http.Headers("Content-type: application/json")
    @GET("queues/{stationId}")
    Call<Object> getQueueVehicleCount(@Path("stationId") String stationId);

    @retrofit2.http.Headers("Content-type: application/json")
    @GET("stations/fuel/{stationId}")
    Call<FuelList> getFulesInAStation(@Path("stationId") String stationId);

    @retrofit2.http.Headers("Content-type: application/json")
    @GET("queues/{stationId}/{vehicleType}")
    Call<Object> getQueueLengthByVehicle(@Path("stationId") String stationId,@Path("vehicleType") String vehicleType);

    @retrofit2.http.Headers("Content-type: application/json")
    @GET("queues/waitTime/{stationId}")
    Call<Object> getQueueWaitingTime(@Path("stationId") String stationId);

    @retrofit2.http.Headers("Content-type: application/json")
    @POST("queues")
    Call<Object> joinAQueue(@Body Queue queue);

    @retrofit2.http.Headers("Content-type: application/json")
    @GET("queues/check/joined/queue/{vehicleId}")
    Call<Object> checkJoinedVehicle(@Path("vehicleId") String vehicleId);

    @retrofit2.http.Headers("Content-type: application/json")
    @PUT("queues/{stationId}/{vehicleId}")
    Call<Object> updateJoinedQueue(@Path("stationId") String stationId, @Path("vehicleId") String vehicleId, @Body Queue queue);

    @retrofit2.http.Headers("Content-type: application/json")
    @PUT("stations/{id}/{fuelType}")
    Call<Object> updateFuelStock(@Path("id") String id, @Path("fuelType") String fuelType, @Body Fuel fuel);

    @retrofit2.http.Headers("Content-type: application/json")
    @GET("users/{email}")
    Call<StatList> viewStationsOfOwner(@Path("email") String email);

    @retrofit2.http.Headers("Content-type: application/json")
    @GET("users/profiles/{email}")
    Call<Object> viewUserProfile(@Path("email") String email);

}
