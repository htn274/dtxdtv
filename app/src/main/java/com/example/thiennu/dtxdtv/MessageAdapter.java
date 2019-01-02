package com.example.thiennu.dtxdtv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_SENT = 0;
    private static final int VIEW_TYPE_RECEIVED = 1;
    Context context;
    List<Message> messages;

    MessageAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).type;
//        if (messages.get(position).user.phone_number.equals(LocalData.phoneNumber)) {
//            return VIEW_TYPE_SENT;
//        }
//        else {
//            return VIEW_TYPE_RECEIVED;
//        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.d("btag", "onCreateViewHolder");
        if (viewType == VIEW_TYPE_SENT) {
            return new SentMessageHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_sent, viewGroup, false));
        } else if (viewType == VIEW_TYPE_RECEIVED) {
            return new ReceivedMessageHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_recieved, viewGroup, false));
        }
        Log.d("btag", "onCreateViewHolder succeed");
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Log.d("btag", "" + viewHolder.getItemViewType());
        if (viewHolder.getItemViewType() == VIEW_TYPE_SENT) {
            Log.d("btag", "onBindViewHolder SENT");
            ((SentMessageHolder) viewHolder).bind(messages.get(position));
            Log.d("btag", "onBindViewHolder SENT succeed");
        } else if (viewHolder.getItemViewType() == VIEW_TYPE_RECEIVED) {
            Log.d("btag", "onBindViewHolder RECEIVE");
            ((ReceivedMessageHolder) viewHolder).bind(messages.get(position));
            Log.d("btag", "onBindViewHolder RECEIVE succeed");
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
