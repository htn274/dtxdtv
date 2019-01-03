package com.example.thiennu.dtxdtv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class group_trip_members extends Fragment {
    String groupId;
    ListView lvMembers;

    public group_trip_members() {
    }

    @SuppressLint("ValidFragment")
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
        final Context context = getContext();
        Log.d("btag", "onCreateView Members");
        Backend.getUsersInGroup(groupId, new MyCallback<ArrayList<User>>() {
            @Override
            public void call(ArrayList<User> res) {
                assert context != null;
                Log.d("btag", "users received " + res.size() + " " + res.get(1).phone_number);
                lvMembers.setAdapter(new UserAdapter(context, 0, res));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lvMembers.invalidate();
                    }
                });
            }
        });
        return v;
    }

}
