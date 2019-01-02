package com.example.thiennu.dtxdtv;

import java.util.ArrayList;

public class TripInfo {
    String id;
    ArrayList<String> members;
    String fromDate;
    String toDate;
    private String trip_name;


    public TripInfo(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getTrip_name() {
        return trip_name;
    }
}
