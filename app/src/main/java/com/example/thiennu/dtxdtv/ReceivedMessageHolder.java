package com.example.thiennu.dtxdtv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, nameText;
    public ReceivedMessageHolder(@NonNull View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.textViewMessage);
        nameText = itemView.findViewById(R.id.textViewUserName);
    }

    void bind(Message message) {
        messageText.setText(message.message);
        nameText.setText(message.user.name);
    }
}
