package com.example.thiennu.dtxdtv;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;

import java.io.Serializable;
import java.util.ArrayList;

public class PlacesMap extends FragmentActivity implements OnMapReadyCallback, Serializable {

    public ArrayList<Place_In_Plan> arrPlaces;
    private GoogleMap mMap;

    public static BitmapDrawable writeOnDrawable(Context context, int drawableId, String text) {

        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), drawableId).copy(Bitmap.Config.ARGB_8888, true);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        Canvas canvas = new Canvas(bm);

        // Change the position of text here
        canvas.drawText(text, bm.getWidth() / 2 //x position
                , bm.getHeight() / 2  // y position
                , paint);
        return new BitmapDrawable(context.getResources(), bm);
    }

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
        addMarker();
    }

    public void addMarker() {
        double centerLongtitude = 0, centerLatitude = 0;

        int numPlaces = arrPlaces.size();
        if (numPlaces > 0) {
            IconGenerator icg = new IconGenerator(this);
            icg.setTextAppearance(R.style.iconGenText);
            icg.setColor(Color.BLUE);
            for (int i = 0; i < numPlaces; i++) {
                Bitmap bm = icg.makeIcon(String.valueOf(i + 1));
                mMap.addMarker(new MarkerOptions().position(arrPlaces.get(i).location).title(arrPlaces.get(i).name)
                        .icon(BitmapDescriptorFactory.fromBitmap(bm)));
                centerLongtitude += arrPlaces.get(i).location.longitude;
                centerLatitude += arrPlaces.get(i).location.latitude;

                if (i < numPlaces - 1) {
                    drawRoute(arrPlaces.get(i).location, arrPlaces.get(i + 1).location);
                }
            }

            LatLng defaultLoc = new LatLng(centerLatitude / numPlaces, centerLongtitude / numPlaces);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLoc, 15));
        }
    }

    public void drawRoute(LatLng source, LatLng destination) {
        new GetPathFromLocation(source, destination, R.color.colorRouteLine, new DirectionPointListener() {
            @Override
            public void onPath(PolylineOptions polyLine) {
                mMap.addPolyline(polyLine);
            }
        }).execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
