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
        btn_add = view.findViewById(R.id.btnAddPlan);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_LONG).show();
            }
        });
        return  view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        edit_date = ((EditText)getActivity().findViewById(R.id.editText_date));
        edit_date.setText("sfgsdfgsdh");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edit_date = (EditText) view.findViewById(R.id.editText_date);
        edit_date.setText("Hello");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        edit_date = ((EditText)getActivity().findViewById(R.id.editText_date));
        edit_date.setText("sfgsdfgsdh");
    }

    @Override
    public void onResume() {
        super.onResume();
        edit_date = ((EditText)getActivity().findViewById(R.id.editText_date));
        edit_date.setText("sfgsdfgsdh");
    }
}
