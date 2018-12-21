package com.example.thiennu.dtxdtv;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class User {
    public String phone_number;
    public String name;
}

class Discussion {
    public String text;
    public String user;
    public double time;
}

class Place {
    public String name;
    public String time;
}

class Plan {
    public String group_id;
    public String group_name;
    public String start;
    public String end;
    public ArrayList<String> members;
    public ArrayList<Discussion> discussions;
    public ArrayList<Place> places;
}

interface MyCallback<T> {
    void call(T res);
}

public class LocalData {
    static public String phoneNumber;

    static void Login(Context context, String phone, String pass, final MyCallback<Boolean> cb) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest stringRequest = null;
        try {
            stringRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    "http://167.99.138.220:8174/signin",
                    new JSONObject().put("phone_number", phone).put("password", pass),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                cb.call(response.getBoolean("ok"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                assert 1 == 0;
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("btag", "rjp");
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestQueue.add(stringRequest);
    }

    static void createTrip(Context context, String tripName, String[] memberList, String fromData, String toDate, final MyCallback<String> cb) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest stringRequest = null;
        try {
            stringRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    "http://167.99.138.220:8174/creategroup",
                    new JSONObject().put("group_name", tripName)
                            .put("members", memberList)
                            .put("start", fromData)
                            .put("end", toDate),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                cb.call(response.getString("group_id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                assert 1 == 0;
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("btag", "rjp");
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestQueue.add(stringRequest);
    }
}
