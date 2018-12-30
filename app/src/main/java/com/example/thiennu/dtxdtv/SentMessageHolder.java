package com.example.thiennu.dtxdtv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SentMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText;
    public SentMessageHolder(@NonNull View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.textViewMessage);
    }

    void bind(Message message) {
        messageText.setText(message.message);
    }
}
