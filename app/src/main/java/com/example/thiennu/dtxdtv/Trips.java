package com.example.thiennu.dtxdtv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Trips extends AppCompatActivity {
    private ListView lvTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
        getSupportActionBar().setTitle("My Trips");
        lvTrips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), groupTrip.class);
                intent.putExtra("groupName", ((TripInfo) lvTrips.getItemAtPosition(position)).getTrip_name());
                intent.putExtra("groupId", ((TripInfo) lvTrips.getItemAtPosition(position)).id);
                startActivity(intent);
            }
        });
        lvTrips = findViewById(R.id.lvTrips);
        updateTripList();
    }

    @Override
    protected void onResume() {
        updateTripList();
        super.onResume();
    }

    public void updateTripList() {
        TripListAdapter customAdaper = new TripListAdapter(this, R.layout.trip_info, LocalData.getTripList());
        customAdaper.notifyDataSetChanged();
        lvTrips.setAdapter(customAdaper);
    }

    public void addGroup(View view) {
        startActivity(new Intent(this, createTrip.class));
    }
}
