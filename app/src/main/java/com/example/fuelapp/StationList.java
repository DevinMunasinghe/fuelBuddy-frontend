package com.example.fuelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class StationList extends AppCompatActivity {

    ListView stationListView;
    String[] stationArray={"lili","devin","senal","saman","kukula"};
    String[] vehicleCountArray ={"1","2","3","4","5","6"};
    String[] stationAvailabiltyArray={"li3li","deverein","senaerel","sama33n","ku33kula"};

//    private ArrayList stationName,stationAvailabilty;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);

        stationListView = findViewById(R.id.stationListView);
        CustomAdapter customAdapter = new CustomAdapter();

        stationListView.setAdapter(customAdapter);
        stationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(),StationData.class);
                intent.putExtra("stationName",stationArray[i]);
                intent.putExtra("stationAvailability",stationAvailabiltyArray[i]);
                intent.putExtra("vehicleCount",vehicleCountArray[i]);

                startActivity(intent);
            }
        });


    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return stationArray.length;
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

            stationName.setText(stationArray[i]);
            vehicleCount.setText(vehicleCountArray[i]);
            stationAvailability.setText(stationAvailabiltyArray[i]);

            return  view1;
        }
    }
}