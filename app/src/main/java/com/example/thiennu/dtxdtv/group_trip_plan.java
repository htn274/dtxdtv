package com.example.thiennu.dtxdtv;


import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 */
public class group_trip_plan extends Fragment implements View.OnClickListener {

    public String groupID;
    public EditText edit_date, edit_time, edit_destination;
    public Button btn_add;
    public ListView lv_places;
    public PlanAdapter planAdapter;
    ArrayList<Place> arrPlaces;

    public group_trip_plan(){

    }

    @SuppressLint("ValidFragment")
    public group_trip_plan(String group_id) {
        super();
        this.groupID = group_id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_trip_plan, container, false);

        edit_date = view.findViewById(R.id.editText_date);
        edit_time = view.findViewById(R.id.editText_time);
        DateSetter dateSetter = new DateSetter(edit_date, view.getContext());
        TimeSetter timeSetter = new TimeSetter(edit_time, view.getContext());

        btn_add = view.findViewById(R.id.btn_addPlace);
        btn_add.setOnClickListener((View.OnClickListener) this);

        edit_destination =  view.findViewById(R.id.edittext_destination);

        arrPlaces = new ArrayList<>();
        lv_places = view.findViewById(R.id.lv_places);
        setPlanAdapter();
        return  view;
    }

    private void setPlanAdapter(){
        LocalData.getPlaceInGroup(getActivity(), groupID, new MyCallback<ArrayList<Place>>() {
            @Override
            public void call(ArrayList<Place> res) {
                arrPlaces = res;
                sortArrayByDateTime(arrPlaces);
                planAdapter = new PlanAdapter(getActivity(), R.layout.plan_layout, arrPlaces);
                lv_places.setAdapter(planAdapter);
            }
        });
    }

    public void sortArrayByDateTime(ArrayList<Place> arr){
        Log.d("Nunu", "sortArrayByDateTime: ");
        Collections.sort(arr, new Comparator<Place>() {
            DateFormat f = new SimpleDateFormat("HH:mm dd-mm-yyyy");
            @Override
            public int compare(Place lhs, Place rhs) {
                try {
                    return f.parse(lhs.time).compareTo(f.parse(rhs.time));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v == btn_add){
            final String dateTime = edit_time.getText().toString() + " " + edit_date.getText().toString();
//            final String dateTime = edit_date.getText().toString() + " " + edit_time.getText().toString();
            planAdapter.notifyDataSetChanged();
            LocalData.AddPlace(getActivity().getApplicationContext(), groupID, edit_destination.getText().toString(), dateTime, new MyCallback<Boolean>() {
                @Override
                public void call(Boolean res) {
                    if (res == null){
                        Toast.makeText(getActivity().getApplicationContext(), "Add place failed, please try again", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(getActivity().getApplicationContext(), "Add place succeed", Toast.LENGTH_SHORT).show();
                        arrPlaces.add(new Place(edit_destination.getText().toString(), dateTime));
                        sortArrayByDateTime(arrPlaces);
                        planAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}


