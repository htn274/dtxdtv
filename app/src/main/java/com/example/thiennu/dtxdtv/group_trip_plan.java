package com.example.thiennu.dtxdtv;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class group_trip_plan extends Fragment implements View.OnClickListener {

    public String groupID;
    public EditText edit_date, edit_time, edit_destination;
    public Button btn_add;
    public ListView lv_places;


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
        return  view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_add){
            String dateTime = edit_time.getText().toString() + edit_date.getText().toString();
            LocalData.AddPlace(getActivity().getApplicationContext(), groupID, edit_destination.getText().toString(), dateTime, new MyCallback<Boolean>() {
                @Override
                public void call(Boolean res) {
                    if (res == null){
                        Toast.makeText(getActivity().getApplicationContext(), "Add place failed, please try again", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(getActivity().getApplicationContext(), "Add place succeed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
