
package com.example.thiennu.dtxdtv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {

    public String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ((Button)findViewById(R.id.btn_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                phone = ((EditText)findViewById(R.id.editText_phonenumber)).getText().toString();
                String pass = ((EditText)findViewById(R.id.editText_password)).getText().toString();
                LocalData.Login(getApplicationContext(), phone, pass, new MyCallback<Boolean>() {
                    @Override
                    public void call(Boolean response) {
                        if (response) {
                            loginOnlick(v);
                            LocalData.phoneNumber = phone;
                        } else {
                            Toast.makeText(getApplicationContext(),"Incorrect phonenumber or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        ((Button)findViewById(R.id.buttonSignUp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loginOnlick(View view) {
        Intent intent = new Intent(this, dashboard.class);
        intent.putExtra("phone", phone);
        startActivity(intent);
        finish();
    }
}