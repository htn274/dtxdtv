package com.example.thiennu.dtxdtv;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class createTrip extends Activity implements View.OnClickListener {

    EditText tripName;
    EditText memberList;
    Button createButton;
    DateSetter dateSetter;
    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private DateSetter dateSetter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        findViewsById();
        setDateTimeField();
    }

    private void findViewsById() {
        fromDateEtxt = findViewById(R.id.editText_dateFrom);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
//        dateSetter = new DateSetter(fromDateEtxt, getApplicationContext());

        toDateEtxt = findViewById(R.id.editText_dateTo);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
//        dateSetter1 = new DateSetter(toDateEtxt, getApplicationContext());

        createButton = findViewById(R.id.btn_create);
        createButton.setOnClickListener(this);

        memberList = findViewById(R.id.editText_memberslist);
        try {
            memberList.setText(LocalData.getPhoneNumber());
        } catch (DataException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        tripName = findViewById(R.id.editText_tripName);
    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if (view == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if (view == toDateEtxt) {
            toDatePickerDialog.show();
        } else if (view == createButton) {
            if (TextUtils.isEmpty(tripName.getText().toString())) {
                tripName.setError("Trip name must not be empty");
            } else if (TextUtils.isEmpty((memberList.getText().toString()))) {
                memberList.setError("Trip name must not be empty");
            } else if (TextUtils.isEmpty((fromDateEtxt.getText().toString()))) {
                fromDateEtxt.setError("Trip name must not be empty");
            } else if (TextUtils.isEmpty((toDateEtxt.getText().toString()))) {
                toDateEtxt.setError("Trip name must not be empty");
            } else {
                Backend.createTrip(
                        tripName.getText().toString(),
                        memberList.getText().toString().split(" "),
                        fromDateEtxt.getText().toString(),
                        toDateEtxt.getText().toString(), new MyCallback<String>() {
                            @Override
                            public void call(String res) {
                                if (res == null) {
                                    Toast.makeText(getApplicationContext(), "Create trip failed, please try again", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Create trip succeed", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });

            }
        }
    }

}
