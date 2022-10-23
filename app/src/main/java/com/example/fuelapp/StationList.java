package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fuelapp.APIManager.Fuel;
import com.example.fuelapp.APIManager.RetrofitClient;
import com.example.fuelapp.APIManager.StationDet;
import com.example.fuelapp.APIManager.StationLists;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationList extends AppCompatActivity {

    ListView stationListView;

    String[] ids;
    String[] vehicleLength;
    String[] stationNames;
    String[] addresses ;
    String[] displayAddresses ;
    String[] phone ;
    Fuel fuels[] = new Fuel[0];
    String[] fuelTypes ;
    String[] fuelArrivals ;
    String[] fuelCompletes ;
    String[] fuelStatus ;
    String[] fuelAvailability ;
    int length;
    String message = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getStations();
        setContentView(R.layout.activity_station_list);

        Intent i = new Intent();
        String vehicleId = i.getStringExtra("vehicleId");

        stationListView = findViewById(R.id.stationListView);
        CustomAdapter customAdapter = new CustomAdapter();

        stationListView.setAdapter(customAdapter);
        stationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(),StationData.class);
                intent.putExtra("stationName",stationNames[i]);
                intent.putExtra("stationAvailability",phone[i]);
                intent.putExtra("vehicleCount",vehicleLength[i]);
                intent.putExtra("stationId",ids[i]);
                startActivity(intent);
            }
        });




    }

    public  void getStations(){

        Call<StationLists> call = RetrofitClient.getInstance().getMyApi().viewStations();

        call.enqueue(new Callback<StationLists>  () {
            @Override
            public void onResponse(Call<StationLists>  call, Response<StationLists>   response) {

                Gson gson = new Gson();

                List<StationDet> list = response.body().getStations();
                int arrayLength = list.size();
                ids = new String[list.size()];
                stationNames = new String[list.size()];
                addresses = new String[list.size()];
                displayAddresses = new String[list.size()];
                phone = new String[list.size()];
                List<String> email = new ArrayList<>();
                Fuel fuels[] = new Fuel[0];
                List<String> fuelTypes = new ArrayList<>();
                List<String> fuelArrivals = new ArrayList<>();
                List<String> fuelCompletes = new ArrayList<>();
                fuelStatus = new String[list.size()];


                vehicleLength = new String[list.size()];
                fuelAvailability = new String[arrayLength];

                for(int i=0; i<list.size(); i++){
                    getStationQueueCount(list.get(i).getId(),i);
                    ids[i]=list.get(i).getId();
                    stationNames[i] =list.get(i).getName();
                    addresses[i] = list.get(i).getAddress();
                    phone[i] = list.get(i).getPhone();
                    email.add(list.get(i).getEmail());
                    fuels = list.get(i).getFuel();

                    for(int j=0; j<fuels.length; j++ ){
                        fuelTypes.add(fuels[j].getType());
                        fuelArrivals.add(fuels[j].getArrival());
                        fuelCompletes.add(fuels[j].getComplete());
                        fuelStatus[j] = fuels[j].getStatus();
                        if(fuels[j].getStatus().equals("available") ){
                            fuelAvailability[i] = fuelStatus[i];
                        }else {
                            fuelAvailability[i] = "unavailable";
                        }

                    }
                    String value = addresses[i];
                    String [] lastAddress = value.split(",");
                    displayAddresses[i] = lastAddress[1];

                }


                System.out.println(gson.toJsonTree(response.body()));
                length = ids.length;
            }

            @Override
            public void onFailure(Call<StationLists>   call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });

    }

    public void getStationQueueCount(String stationId, int i){
        Call<Object> call = RetrofitClient.getInstance().getMyApi().getQueueVehicleCount(stationId);

        call.enqueue(new Callback<Object> () {
            @Override
            public void onResponse(Call<Object>  call, Response<Object>   response) {
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonElement element = gson.toJsonTree(response.body());
                JsonObject rootObject = element.getAsJsonObject();
                message = rootObject.get("data").getAsString();

                accessData(message,i);


            }
            @Override
            public void onFailure(Call<Object>   call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });

    }

    public void accessData(String vehicleId, int id){
        vehicleLength[id] = vehicleId;
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.station_row_data,null);
            TextView stationName = view1.findViewById(R.id.stationName);
            TextView vehicleCount = view1.findViewById(R.id.vehicleCountList);
            TextView stationAvailability = view1.findViewById(R.id.stationAvailability);
            TextView address = view1.findViewById(R.id.addressValue);

            stationName.setText(stationNames[i]);
            vehicleCount.setText(vehicleLength[i]);
            stationAvailability.setText(fuelAvailability[i]);
            address.setText(displayAddresses[i]);

            return  view1;
        }
    }
}