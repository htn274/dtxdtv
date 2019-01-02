package com.example.thiennu.dtxdtv;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    MessageAdapter messageAdapter;
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
        Log.d("btag", "nooooo");
        View v = inflater.inflate(R.layout.activity_chat, container, false);

        messages = new ArrayList<>();
        messageAdapter = new MessageAdapter(getContext(), messages);
        Log.d("btag", "" + messageAdapter.getItemCount());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        messageRecycler = v.findViewById(R.id.recyclerViewMessages);
        messageRecycler.setLayoutManager(linearLayoutManager);
        messageRecycler.setAdapter(messageAdapter);

        eventLooper = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    List<Message> newMessages = Backend.syncCheckNewMessage(getContext(), group_id, last_updated);

                    messages.addAll(newMessages);
                    messageRecycler.postInvalidate();
//                    messageRecycler.scrollToPosition(messages.size() - 1);

                    if (!messages.isEmpty()) {
                        last_updated = messages.get(messages.size() - 1).time;
                    }
                }
            }
        });

        final EditText editText = v.findViewById(R.id.editTextMessage);
        editText.requestFocus();

        v.findViewById(R.id.buttonSendMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().equals("")) {
                    Backend.sendMessage(getContext(), group_id, editText.getText().toString());
                    editText.setText("");
                }
            }
        });
        eventLooper.start();

        return v;
    }
}
