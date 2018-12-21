package com.example.thiennu.dtxdtv;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class createTrip extends Activity implements View.OnClickListener {

    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    EditText tripName;
    EditText memberList;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    Button createButton;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        findViewsById();

        setDateTimeField();
    }

    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.editText_dateFrom);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        createButton = ((Button)findViewById(R.id.btn_create));

        toDateEtxt = (EditText) findViewById(R.id.editText_dateTo);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener((View.OnClickListener) this);
        toDateEtxt.setOnClickListener((View.OnClickListener) this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if(view == toDateEtxt) {
            toDatePickerDialog.show();
        }
        else if (view == createButton){
            if (tripName.getText().equals("")) {
                Toast.makeText(getApplicationContext(), "Trip name must not be empty", Toast.LENGTH_SHORT);
            }
            else if (memberList.getText().equals("")) {
                Toast.makeText(getApplicationContext(), "Member list must not be empty", Toast.LENGTH_SHORT);
            }
            else if (fromDateEtxt.getText().equals("")) {
                Toast.makeText(getApplicationContext(), "Trip name must not be empty", Toast.LENGTH_SHORT);
            }
            else if (toDateEtxt.getText().equals("")) {
                Toast.makeText(getApplicationContext(), "Trip name must not be empty", Toast.LENGTH_SHORT);
            }
            else {
                LocalData.createTrip(
                        getApplicationContext(),
                        tripName.getText().toString(),
                        memberList.getText().toString().split(" "),
                        fromDateEtxt.getText().toString(),
                        toDateEtxt.getText().toString(), new LocalData().MyCallback<Boolean>() {

                });

            }
        }
    }

}
