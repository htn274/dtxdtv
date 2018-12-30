package com.example.thiennu.dtxdtv;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class User {
    public String phone_number;
    public String name;
}

class Discussion {
    public String text;
    public String user;
    public double time;
}

class Place_In_Plan {
    public String name;
    public String time;
    public LatLng location;
//    public String date;
    public Place_In_Plan(){

    }

    public Place_In_Plan(String name, String time, LatLng location){
        this.name = name;
        this.time = time;
        this.location = location;
    }
}

class Plan {
    public String group_id;
    public String group_name;
    public String start;
    public String end;
    public ArrayList<String> members;
    public ArrayList<Discussion> discussions;
    public ArrayList<Place_In_Plan> placeInPlans;
}

interface MyCallback<T> {
    void call(T res);
}

public class LocalData {
    static public String phoneNumber;
    static public String host = "http://0.0.0.0:8174";// "http://167.99.138.220:8174";
    static void sendRequest(Context context, String url, JSONObject data, Response.Listener<JSONObject> callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest stringRequest = null;
        stringRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                data,
                callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("btag", "rjp");
                    }
                });
        requestQueue.add(stringRequest);
    }

    static void Login(Context context, String phone, String pass, final MyCallback<Boolean> cb) {
        try {
            String url = host + "/signin";
            JSONObject data = new JSONObject().put("phone_number", phone).put("password", pass);
            sendRequest(context, url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        cb.call(response.getBoolean("ok"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        assert 1 == 0;
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            assert 1 == 0;
        }
    }

    static void AddPlace(Context context, final String groupID, String name, String time, LatLng location, final MyCallback<Boolean> cb){
        try {
            String url = host + "/addplace";
            JSONObject data = new JSONObject().put("group_id", groupID).put("name", name).put("time", time)
                    .put("latitude", location.latitude).put("longtitude", location.longitude);
            Log.d("Nunu", name + " " + time);
            sendRequest(context, url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        cb.call(response.getBoolean("ok"));
                        Log.d("Nunu", groupID);
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                        assert 1 == 0;
                    }
                }
            });

        }
        catch (JSONException e){
            e.printStackTrace();
            assert  1 == 0;
        }
    }

    static void createTrip(Context context, String tripName, String[] memberList, String fromData, String toDate, final MyCallback<String> cb) {
        try {
            String url = host + "/creategroup";
            JSONObject data = new JSONObject().put("group_name", tripName)
                    .put("members", new JSONArray(memberList))
                    .put("start", fromData)
                    .put("end", toDate);
            sendRequest(context, url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        cb.call(response.getString("group_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        assert 1 == 0;
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            assert 1 == 0;
        }
    }

    static void getGroupOfUser(Context context, String phone, final MyCallback<ArrayList<TripInfo>> cb) {
        try {
            String url = host + "/groupsofauser";
            JSONObject data = new JSONObject().put("user", phone);
            sendRequest(context, url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray arr = response.getJSONArray("data");
                        int len = arr.length();
                        ArrayList<TripInfo> res = new ArrayList<>();
                        for (int i = 0; i < len; i++) {
                            JSONObject obj = arr.getJSONObject(i);
                            String id = obj.getString("group_id");
                            String name = obj.getString("group_name");
                            String start = obj.getString("start");
                            String end = obj.getString("end");
                            ArrayList<String> members = new ArrayList<>();
                            JSONArray jsonMembers = obj.getJSONArray("members");
                            for (int j = 0; j < jsonMembers.length(); j++) {
                                members.add(jsonMembers.getString(j));
                            }
                            TripInfo info = new TripInfo(name);
                            info.id = id;
                            info.members = members;
                            info.fromDate = start;
                            info.toDate = end;
                            res.add(info);
                        }
                        cb.call(res);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        assert 1 == 0;
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            assert 1 == 0;
        }
    }

    static void getUsersInfo(Context context, ArrayList<String> phones, final MyCallback<ArrayList<User>> cb) {
        try {
            String url = host + "/viewmembers";
            JSONObject data = new JSONObject().put("users", new JSONArray(phones));
            sendRequest(context, url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray arr = response.getJSONArray("users");
                        ArrayList<User> res = new ArrayList<>();
                        for (int i=0; i<arr.length(); i++) {
                            User u = new User();
                            JSONObject o = arr.getJSONObject(i);
                            u.name = o.getString("name");
                            u.phone_number = o.getString("phone_number");
                            res.add(u);
                        }
                        cb.call(res);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void getPlaceInGroup(Context context, String groupID, final MyCallback<ArrayList<Place_In_Plan>> cb){
        try{
            String url = host + "/viewgroup";
            JSONObject data = new JSONObject().put("group_id", groupID);
            sendRequest(context, url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject groupInfo = response.getJSONObject("data");
                        JSONArray arr = groupInfo.getJSONArray("places");
                        ArrayList<Place_In_Plan> res = new ArrayList<>();
                        for (int i = 0; i < arr.length(); i++){
                            Place_In_Plan p = new Place_In_Plan();
                            JSONObject obj = arr.getJSONObject(i);
                            p.name = obj.getString("name");
                            p.time = obj.getString("time");
                            p.location = new LatLng(obj.getDouble("latitude"), obj.getDouble("longtitude"));
                            res.add(p);
                        }
                        cb.call(res);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static void getUsersInGroup(Context context, String groupId, final MyCallback<ArrayList<User>> cb) {
        try {
            String url = host + "/usersingroup";
            JSONObject data = new JSONObject().put("group_id", groupId);
            sendRequest(context, url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray arr = response.getJSONArray("users");
                        ArrayList<User> res = new ArrayList<>();
                        for (int i = 0; i < arr.length(); i++) {
                            User u = new User();
                            JSONObject o = arr.getJSONObject(i);
                            u.name = o.getString("name");
                            u.phone_number = o.getString("phone_number");
                            res.add(u);
                        }
                        cb.call(res);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
    } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static List<Message> syncCheckNewMessage(Context context, String group_id, Double last_updated) {
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        try {
            String url = host + "/getnewmessages";
            JSONObject data = new JSONObject().put("group_id", group_id).put("last_updated", last_updated);
            sendRequest(context, url, data, future);
            JSONObject response = future.get(3, TimeUnit.SECONDS);
            JSONArray jsonArray = response.getJSONArray("messages");
            ArrayList<Message> messages = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject object = jsonArray.getJSONObject(i);
                Message message = new Message();
                message.message = object.getString("text");
                message.user = new User();
                message.user.phone_number = object.getString("user");
                message.type = (message.user.phone_number.equals(LocalData.phoneNumber) ? 0 : 1);
                message.time = object.getDouble("time");
                messages.add(message);
            }
            return messages;
        } catch (JSONException | InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
