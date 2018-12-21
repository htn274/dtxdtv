package com.example.thiennu.dtxdtv;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class group_trip_plan extends Fragment {

    public EditText edit_date, edit_time, edit_destination;
    public Button btn_add;
    public group_trip_plan() {
        // Required empty public constructor
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

        return  view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
