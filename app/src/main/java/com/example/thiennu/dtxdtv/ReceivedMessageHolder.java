package com.example.thiennu.dtxdtv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, phoneText;

    public ReceivedMessageHolder(@NonNull View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.textViewMessage);
        phoneText = itemView.findViewById(R.id.textViewPhone);
    }

    void bind(Message message) {
        messageText.setText(message.message);
        phoneText.setText(message.user.phone_number);
    }
}
