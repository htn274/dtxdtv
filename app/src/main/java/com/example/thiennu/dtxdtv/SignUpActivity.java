package com.example.thiennu.dtxdtv;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
//                Token token = signUp(uname, phone, pass);
//                if (signUpSucceed()) {
//
//                }
            }
        });
    }

}
