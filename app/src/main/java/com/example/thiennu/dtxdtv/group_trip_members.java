package com.example.thiennu.dtxdtv;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class group_trip_members extends Fragment {
    String groupId;
    ListView lvMembers;
    public group_trip_members() {}
    public group_trip_members(String groupId) {
        super();
        this.groupId = groupId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_group_trip_members, container, false);
        lvMembers = v.findViewById(R.id.lvMembers);
        LocalData.getUsersInGroup(getContext(), groupId, new MyCallback<ArrayList<User>>() {
            @Override
            public void call(ArrayList<User> res) {
                lvMembers.setAdapter(new UserAdapter(getContext(), 0, res));
            }
        })
        return v;
    }

}
