package com.example.thiennu.dtxdtv;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((Button)findViewById(R.id.buttonSignUp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = ((EditText)findViewById(R.id.editText_username)).getText().toString();
                String phone = ((EditText)findViewById(R.id.editText_phonenumber)).getText().toString();
                String pass = ((EditText)findViewById(R.id.editText_password)).getText().toString();

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest stringRequest = null;
                try {
                    stringRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            "http://167.99.138.220:8174/signup",
                            new JSONObject().put("phone_number", phone).put("name", uname).put("password", pass),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
//                                    Log.d("btag", response.toString());
                                    finish();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("btag", "rjp");
                                }});
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                requestQueue.add(stringRequest);
//                Token token = signUp(uname, phone, pass);
//                if (signUpSucceed()) {
//
//                }
            }
        });
    }

}