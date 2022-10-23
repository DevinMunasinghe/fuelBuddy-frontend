package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ownerStationList extends AppCompatActivity {
    ListView stationOwnerListView;

    int length;
    String message = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_station_list);

        stationOwnerListView = findViewById(R.id.stationOwnerListView);
        ownerStationList.CustomAdapter customAdapter = new ownerStationList.CustomAdapter();

        stationOwnerListView.setAdapter(customAdapter);
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
            View view1 = getLayoutInflater().inflate(R.layout.station_owner_row_data,null);
            TextView stationName = view1.findViewById(R.id.stationNameProfile);
            TextView stationID = view1.findViewById(R.id.stationIDProfile);
            TextView address = view1.findViewById(R.id.addressValueProfile);


//            stationName.setText(stationNames[i]);
//            vehicleCount.setText(vehicleLength[i]);
//            stationAvailability.setText(fuelAvailability[i]);
//            address.setText(displayAddresses[i]);

            return  view1;
        }
    }
}