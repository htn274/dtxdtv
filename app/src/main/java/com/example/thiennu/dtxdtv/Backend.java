package com.example.thiennu.dtxdtv;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

interface MyCallback<T> {
    void call(T res);
}

class User {
    public String phone_number;
    public String name;
}

class Place_In_Plan implements Parcelable {
    public static final Creator<Place_In_Plan> CREATOR = new Creator<Place_In_Plan>() {
        @Override
        public Place_In_Plan createFromParcel(Parcel in) {
            return new Place_In_Plan(in);
        }

        @Override
        public Place_In_Plan[] newArray(int size) {
            return new Place_In_Plan[size];
        }
    };
    public String name;
    public String time;
    public LatLng location;

    //    public String date;
    public Place_In_Plan() {

    }

    public Place_In_Plan(String name, String time, LatLng location) {
        this.name = name;
        this.time = time;
        this.location = location;
    }

    protected Place_In_Plan(Parcel in) {
        name = in.readString();
        time = in.readString();
        location = in.readParcelable(LatLng.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(time);
        dest.writeParcelable(location, flags);
    }
}

class Backend {
    private static String host = "http://167.99.138.220:8174";
    private static RequestQueue queue;

    static void initBackend(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    private static void sendRequest(String url, JSONObject data, Response.Listener<JSONObject> callback) {

        JsonObjectRequest stringRequest = null;
        stringRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                data,
                callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        queue.add(stringRequest);
    }

    static void Login(String phone, String pass, final MyCallback<Boolean> cb) {
        try {
            String url = host + "/signin";
            JSONObject data = new JSONObject().put("phone_number", phone).put("password", pass);
            sendRequest(url, data, new Response.Listener<JSONObject>() {
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
        }
    }

    static void AddPlace(final String groupID, String name, String time, LatLng location, final MyCallback<Boolean> cb) {
        try {
            String url = host + "/addplace";
            JSONObject data = new JSONObject().put("group_id", groupID).put("name", name).put("time", time)
                    .put("latitude", location.latitude).put("longtitude", location.longitude);
            Log.d("Nunu", name + " " + time);
            sendRequest(url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        cb.call(response.getBoolean("ok"));
                        Log.d("Nunu", groupID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static void createTrip(String tripName, String[] memberList, String fromData, String toDate, final MyCallback<String> cb) {
        try {
            String url = host + "/creategroup";
            JSONObject data = new JSONObject().put("group_name", tripName)
                    .put("members", new JSONArray(memberList))
                    .put("start", fromData)
                    .put("end", toDate);
            sendRequest(url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        cb.call(response.getString("group_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static void getGroupOfUser(String phone, final MyCallback<ArrayList<TripInfo>> cb) {
        try {
            String url = host + "/groupsofauser";
            JSONObject data = new JSONObject().put("user", phone);
            sendRequest(url, data, new Response.Listener<JSONObject>() {
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
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static void getUsersInfo(ArrayList<String> phones, final MyCallback<ArrayList<User>> cb) {
        try {
            String url = host + "/viewmembers";
            JSONObject data = new JSONObject().put("users", new JSONArray(phones));
            sendRequest(url, data, new Response.Listener<JSONObject>() {
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static void getPlaceInGroup(String groupID, final MyCallback<ArrayList<Place_In_Plan>> cb) {
        try {
            String url = host + "/viewgroup";
            JSONObject data = new JSONObject().put("group_id", groupID);
            sendRequest(url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject groupInfo = response.getJSONObject("data");
                        JSONArray arr = groupInfo.getJSONArray("places");
                        ArrayList<Place_In_Plan> res = new ArrayList<>();
                        for (int i = 0; i < arr.length(); i++) {
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void getUsersInGroup(String groupId, final MyCallback<ArrayList<User>> cb) {
        try {
            String url = host + "/usersingroup";
            JSONObject data = new JSONObject().put("group_id", groupId);
            sendRequest(url, data, new Response.Listener<JSONObject>() {
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static List<Message> syncCheckNewMessage(String group_id, Double last_updated) {
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        try {
            String url = host + "/getnewmessages";
            JSONObject data = new JSONObject().put("group_id", group_id).put("last_updated", last_updated);
            sendRequest(url, data, future);
            JSONObject response = future.get(1, TimeUnit.SECONDS);
            JSONArray jsonArray = response.getJSONArray("messages");
            ArrayList<Message> messages = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject object = jsonArray.getJSONObject(i);
                Message message = new Message();
                message.message = object.getString("text");
                message.user = new User();
                message.user.phone_number = object.getString("user");
                message.type = (message.user.phone_number.equals(LocalData.getPhoneNumber()) ? 0 : 1);
                message.time = object.getDouble("time");
                messages.add(message);
            }
            return messages;
        } catch (JSONException | InterruptedException | ExecutionException | TimeoutException | DataException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    static void sendMessage(String group_id, String text) {
        String url = host + "/addchat";
        try {
            JSONObject data = new JSONObject().put("group_id", group_id).put("text", text).put("user", LocalData.getPhoneNumber());
            sendRequest(url, data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                }
            });
        } catch (JSONException | DataException e) {
            e.printStackTrace();
        }
    }
}
