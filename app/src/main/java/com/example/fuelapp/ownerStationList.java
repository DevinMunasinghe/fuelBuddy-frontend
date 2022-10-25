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


import com.example.fuelapp.APIManager.RetrofitClient;
import com.example.fuelapp.APIManager.StatList;
import com.example.fuelapp.APIManager.Station;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Data displaying of all the stations available for a particular station owner
 *
 * @author Hasani Kariyawasam
 *
 * @method getOwnersStations(email) @param email
 * @returns the list of stations
 *
 */

public class ownerStationList extends AppCompatActivity {

    //variables
    ListView stationOwnerListView;

    int length;
    String message = " ";
    String[] stationNames;
    String[] ids ;

    TextView profileName,phoneNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //intent data retrieval
        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String phone = i.getStringExtra("phone");
        String email = i.getStringExtra("email");

        //triggering retrieval of particular owners stations list
        getOwnersStations(email);

        //list view management
        setContentView(R.layout.activity_owner_station_list);
        stationOwnerListView = findViewById(R.id.stationOwnerListView);
        CustomAdapter customAdapter = new CustomAdapter();
        stationOwnerListView.setAdapter(customAdapter);

        //id identification
        profileName =(TextView) findViewById(R.id.profileOwnerNameValue);
        phoneNo = (TextView) findViewById(R.id.profileOwnerPhoneValue);

        //single block click trigger
        stationOwnerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(),updateOwner.class);
                intent.putExtra("stationName",stationNames[i]);
                intent.putExtra("stationId",ids[i]);
                startActivity(intent);
            }
        });

        //intent data setting
        profileName.setText(name);
        phoneNo.setText(phone);
    }

    //retrieve all stations for an owner with web API
    public  void getOwnersStations(String email){

        Call<StatList> call = RetrofitClient.getInstance().getMyApi().viewStationsOfOwner(email);

        call.enqueue(new Callback<StatList>() {
            @Override
            public void onResponse(Call<StatList>  call, Response<StatList> response) {

                if(response.isSuccessful()){
                    Gson gson = new Gson();

                    List<Station> list = response.body().getStations();
                    ids = new String[list.size()];
                    stationNames = new String[list.size()];

                    for(int i=0; i<list.size(); i++){
                        ids[i] = list.get(i).getId();
                        stationNames[i]=list.get(i).getName();
                    }
                    length = ids.length;
                }

            }

            @Override
            public void onFailure(Call<StatList>   call, Throwable t) {
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
            View view = getLayoutInflater().inflate(R.layout.station_owner_row_data,null);
            TextView stationName = view.findViewById(R.id.stationNameProfile);
            TextView stationID = view.findViewById(R.id.stationIDProfile);
//            TextView address = view.findViewById(R.id.addressValueProfile);


            stationName.setText(stationNames[i]);
            stationID.setText(ids[i]);

            return  view;
        }
    }
}