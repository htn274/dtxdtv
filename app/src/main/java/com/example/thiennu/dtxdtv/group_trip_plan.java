package com.example.thiennu.dtxdtv;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 */
public class group_trip_plan extends Fragment implements View.OnClickListener {

    public String groupID;
    public EditText edit_date, edit_time;
    public Button btn_add, btn_showMap;
    public ListView lv_places;
    public PlanAdapter planAdapter;
    public PlaceAutocompleteFragment placeAutocompleteFragment;
    protected GeoDataClient mGeoDataClient;
    protected PlaceDetectionClient mPlaceDetectionClient;
    ArrayList<Place_In_Plan> arrPlaces;
    Place chosen_place;

    public group_trip_plan() {

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
        //Hide keyboard
        edit_date.setInputType(InputType.TYPE_NULL);
        edit_time.setInputType(InputType.TYPE_NULL);

        DateSetter dateSetter = new DateSetter(edit_date, view.getContext());
        TimeSetter timeSetter = new TimeSetter(edit_time, view.getContext());

        btn_add = view.findViewById(R.id.btn_addPlace);
        btn_add.setOnClickListener((View.OnClickListener) this);
        btn_showMap = view.findViewById(R.id.btn_showPlacesMap);
        btn_showMap.setOnClickListener((View.OnClickListener) this);

        arrPlaces = new ArrayList<>();
        lv_places = view.findViewById(R.id.lv_places);
        setPlanAdapter();

        searchPlace();
        return view;
    }

    private void setPlanAdapter() {
        Log.d("Nunu", "set plan adapter: ");
        LocalData.getPlaceInGroup(getActivity(), groupID, new MyCallback<ArrayList<Place_In_Plan>>() {
            @Override
            public void call(ArrayList<Place_In_Plan> res) {
                arrPlaces = res;
                Log.d("Nunu", "set plan adapter: " + String.valueOf(arrPlaces.size()));
                sortArrayByDateTime(arrPlaces);
                planAdapter = new PlanAdapter(getActivity(), R.layout.plan_layout, arrPlaces);
                lv_places.setAdapter(planAdapter);
            }
        });
    }

    public void sortArrayByDateTime(ArrayList<Place_In_Plan> arr) {
        Log.d("Nunu", "sortArrayByDateTime: ");
        Collections.sort(arr, new Comparator<Place_In_Plan>() {
            DateFormat f = new SimpleDateFormat("HH:mm dd-mm-yyyy");

            @Override
            public int compare(Place_In_Plan lhs, Place_In_Plan rhs) {
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
        if (v == btn_add) {
            if (chosen_place == null) {
                Toast.makeText(getActivity().getApplicationContext(), "You must chose place.", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(edit_date.getText().toString()) || TextUtils.isEmpty(edit_time.getText().toString())) {
                Toast.makeText(getActivity().getApplicationContext(), "You must set time and date", Toast.LENGTH_LONG).show();
            } else {
                final String dateTime = edit_time.getText().toString() + " " + edit_date.getText().toString();
                planAdapter.notifyDataSetChanged();
                LocalData.AddPlace(getActivity().getApplicationContext(), groupID, chosen_place.getName().toString(),
                        dateTime, chosen_place.getLatLng(), new MyCallback<Boolean>() {
                            @Override
                            public void call(Boolean res) {
                                if (res == false) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Add place failed, please try again", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Add place succeed", Toast.LENGTH_SHORT).show();
                                    arrPlaces.add(new Place_In_Plan(chosen_place.getName().toString(), dateTime, chosen_place.getLatLng()));
                                    sortArrayByDateTime(arrPlaces);
                                    planAdapter.notifyDataSetChanged();
                                }
                            }
                        });
            }
        } else if (v == btn_showMap) {
            if (arrPlaces.size() > 0) {
                Intent intent = new Intent(getActivity(), PlacesMap.class);
                intent.putParcelableArrayListExtra("placesList", arrPlaces);
                startActivity(intent);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "No place to show.", Toast.LENGTH_LONG);
            }
        }

    }

    public void searchPlace() {
        assert getFragmentManager() != null;
        PlaceAutocompleteFragment placeAutocompleteFragment = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        // Filter result
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE)
                .build();
        placeAutocompleteFragment.setFilter(typeFilter);

        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("Nunu Search place", "Place: " + place.getName());
                chosen_place = place;
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("Nunu Search place", "An error occurred: " + status);
            }
        });
    }
}


