package com.example.thiennu.dtxdtv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class group_trip_discussion extends Fragment {
    RecyclerView messageRecycler;
    String group_id;
    Thread eventLooper;
    ArrayList<Message> messages;
    Double last_updated = -1.0;

    public group_trip_discussion() {
    }

    @SuppressLint("ValidFragment")
    public group_trip_discussion(String group_id) {
        super();
        this.group_id = group_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_chat, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        messageRecycler = v.findViewById(R.id.recyclerViewMessages);
        messageRecycler.setLayoutManager(linearLayoutManager);
        messages = new ArrayList<>();

        final FragmentActivity ac = getActivity();
        eventLooper = new Thread(new Runnable() {
            @Override
            public void run() {
                assert ac != null;
                while (!ac.isDestroyed()) {
                    List<Message> newMessages = Backend.syncCheckNewMessage(group_id, last_updated);
                    if (!newMessages.isEmpty()) {
                        messages.addAll(newMessages);
                        last_updated = newMessages.get(newMessages.size() - 1).time;
                        ac.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MessageAdapter adapter = new MessageAdapter(getContext(), messages);
                                messageRecycler.setAdapter(adapter);
                                messageRecycler.invalidate();
                                messageRecycler.scrollToPosition(messages.size());
                            }
                        });
                    }
                }
            }
        });

        final EditText editText = v.findViewById(R.id.editTextMessage);
        // editText.requestFocus();
        v.findViewById(R.id.buttonSendMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().equals("")) {
                    Backend.sendMessage(group_id, editText.getText().toString());
                    editText.setText("");
                }
            }
        });
        eventLooper.start();

        return v;
    }
}
