package com.example.thiennu.dtxdtv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {

    public String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        setContentView(R.layout.activity_login);
        ((Button) findViewById(R.id.btn_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                phone = ((EditText) findViewById(R.id.editText_phonenumber)).getText().toString();
                String pass = ((EditText) findViewById(R.id.editText_password)).getText().toString();
                LocalData.Login(getApplicationContext(), phone, pass, new MyCallback<Boolean>() {
                    @Override
                    public void call(Boolean response) {
                        if (response) {
                            loginOnlick(v);
                            LocalData.phoneNumber = phone;
                        } else {
                            Toast.makeText(getApplicationContext(), "Incorrect phonenumber or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        ((TextView) findViewById(R.id.buttonSignUp)).setOnClickListener(new View.OnClickListener() {
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