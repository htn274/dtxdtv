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

    public TripListAdapter customAdaper;
    ArrayList<TripInfo> tripList;
    private ListView lvTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
        getSupportActionBar().setTitle("My Trips");
        tripList = new ArrayList<>();
        getTripList();
        lvTrips = findViewById(R.id.lvTrips);
    }

    @Override
    protected void onResume() {
        getTripList();
        super.onResume();
    }

    public void setLvTrips() {
        customAdaper = new TripListAdapter(this, R.layout.trip_info, tripList);
        customAdaper.notifyDataSetChanged();
        lvTrips.setAdapter(customAdaper);
        lvTrips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), groupTrip.class);
                intent.putExtra("groupName", ((TripInfo) lvTrips.getItemAtPosition(position)).getTrip_name());
                intent.putExtra("groupId", ((TripInfo) lvTrips.getItemAtPosition(position)).id);
                startActivity(intent);
            }
        });
    }


    public void getTripList() {
        String phone = null;
        try {
            phone = LocalData.getPhoneNumber();
        } catch (DataException e) {
            Toast.makeText(getApplicationContext(), e.errorMessage(), Toast.LENGTH_SHORT).show();
        }
        Backend.getGroupOfUser(getApplicationContext(), phone, new MyCallback<ArrayList<TripInfo>>() {
            @Override
            public void call(ArrayList<TripInfo> res) {
                tripList = res;
                if (tripList.size() > 0) {
                    setLvTrips();
                }
            }
        });

    }


    public void addGroup(View view) {
        startActivity(new Intent(this, createTrip.class));

    }
}
