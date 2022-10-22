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
    String[] stationArray={"lili","devin","senal","saman","kukula"};
    String[] vehicleCountArray ={"1","2","3","4","5","6"};
    String[] stationAvailabiltyArray={"li3li","deverein","senaerel","sama33n","ku33kula"};

    String[] ids;
    String[] queueIds;
    String[] stationNames;
    String[] address ;
    String[] phone ;
    Fuel fuels[] = new Fuel[0];
    String[] fuelTypes ;
    String[] fuelArrivals ;
    String[] fuelCompletes ;
    String[] fuelStatus ;
    String[] fuelAvailability ;
    int length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getStations();
        setContentView(R.layout.activity_station_list);

        stationListView = findViewById(R.id.stationListView);
        CustomAdapter customAdapter = new CustomAdapter();

        stationListView.setAdapter(customAdapter);
        stationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(),StationData.class);
                intent.putExtra("stationName",stationNames[i]);
                intent.putExtra("stationAvailability",phone[i]);
                intent.putExtra("vehicleCount",queueIds[i]);

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
                List<String> address = new ArrayList<>();
                phone = new String[list.size()];
                List<String> email = new ArrayList<>();
                Fuel fuels[] = new Fuel[0];
                List<String> fuelTypes = new ArrayList<>();
                List<String> fuelArrivals = new ArrayList<>();
                List<String> fuelCompletes = new ArrayList<>();
                fuelStatus = new String[list.size()];


                queueIds = new String[list.size()];
                fuelAvailability = new String[arrayLength];

                for(int i=0; i<list.size(); i++){
                    getStationQueueCount(list.get(i).getId(),i);
                    ids[i]=list.get(i).getId();
                    stationNames[i] =list.get(i).getName();
                    address.add(list.get(i).getAddress());
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

                }



                for(String i: ids){
                    System.out.println(gson.toJsonTree(i));
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
                String message = rootObject.get("data").getAsString();

                System.out.println(message);
                queueIds[i] = message;

            }
            @Override
            public void onFailure(Call<Object>   call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });


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
            TextView vehicleCount = view1.findViewById(R.id.vehicleCount);
            TextView stationAvailability = view1.findViewById(R.id.stationAvailability);

            stationName.setText(stationNames[i]);
            vehicleCount.setText(queueIds[i]);
            stationAvailability.setText(fuelAvailability[i]);

            return  view1;
        }
    }
}