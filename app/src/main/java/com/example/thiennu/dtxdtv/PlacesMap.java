package com.example.thiennu.dtxdtv;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class PlacesMap extends FragmentActivity implements OnMapReadyCallback, Serializable {

    private GoogleMap mMap;
    public ArrayList<Place_In_Plan> arrPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent = getIntent();
        arrPlaces = intent.getExtras().getParcelableArrayList("placesList");
        double centerLongtitude = 0, centerLatitude = 0;

        int numPlaces = arrPlaces.size();
        if (intent != null && numPlaces > 0)
        {
            for (int i = 0; i < numPlaces; i++){

                mMap.addMarker(new MarkerOptions().position(arrPlaces.get(i).location));
                centerLongtitude += arrPlaces.get(i).location.longitude;
                centerLatitude += arrPlaces.get(i).location.latitude;
            }
            LatLng defaultLoc = new LatLng(centerLatitude/numPlaces, centerLongtitude/numPlaces);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLoc, 15));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
