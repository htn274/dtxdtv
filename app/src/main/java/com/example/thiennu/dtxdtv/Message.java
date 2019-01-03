package com.example.thiennu.dtxdtv;

public class Message {
    public int type;
    public double time;
    String message;
    User user;

    public Message text(String text) {
        message = text;
        return this;
    }
}
