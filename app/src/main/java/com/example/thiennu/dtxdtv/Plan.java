package com.example.thiennu.dtxdtv;

public class Plan {
    public String name;
    public String time;

    public Plan Name(String name) {
        this.name = name;
        return this;
    }

    public Plan Time(String time) {
        this.time = time;
        return this;
    }
}
