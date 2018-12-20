package com.example.thiennu.dtxdtv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Trips extends AppCompatActivity {

    ArrayList<TripInfo> tripList;
    private ListView lvTrips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
        tripList = new ArrayList<>();
        tripList.add(new TripInfo("Trip to Hue"));
        lvTrips = (ListView) findViewById(R.id.lvTrips);
        TripListAdapter customAdaper = new TripListAdapter(this,R.layout.trip_info, tripList);
        lvTrips.setAdapter(customAdaper);
        lvTrips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), groupTrip.class);
                startActivity(intent);
            }
        });
    }

    public void addGroup(View view) {
        startActivity(new Intent(this, createTrip.class));
    }
}
