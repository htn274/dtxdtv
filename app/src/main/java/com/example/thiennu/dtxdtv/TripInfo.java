package com.example.thiennu.dtxdtv;

import java.util.ArrayList;

public class TripInfo {
    private String trip_name;
    ArrayList<String> members;
    String fromDate;
    String toDate;


    public TripInfo(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getTrip_name() {
        return trip_name;
    }
}
