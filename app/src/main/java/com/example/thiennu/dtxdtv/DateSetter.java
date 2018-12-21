package com.example.thiennu.dtxdtv;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateSetter implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener, View.OnClickListener, View.OnTouchListener {

        private EditText mEditText;
        private Calendar mCalendar;
        private SimpleDateFormat mFormat;
        private Context mContext;

        public DateSetter(EditText editText, Context context){
            this.mEditText = editText;
            this.mEditText.setOnFocusChangeListener(this);
            this.mEditText.setOnClickListener(this);
            this.mEditText.setOnTouchListener(this);
            this.mContext = context;
        }

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus && view == mEditText){
                showPicker(view);
            }
        }

        @Override
        public void onClick(View view) {
            if (view == mEditText)
                showPicker(view);
        }

        private void showPicker(View view) {
            if (mCalendar == null)
                mCalendar = Calendar.getInstance();

            int day = mCalendar.get(Calendar.DAY_OF_MONTH);
            int month = mCalendar.get(Calendar.MONTH);
            int year = mCalendar.get(Calendar.YEAR);

            new DatePickerDialog(mContext, this, year, month, day).show();
        }

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
            final Date startDate = newDate.getTime();
            String fdate = sd.format(startDate);

            mEditText.setText(fdate);
        }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
            showPicker(v);
        return false;
    }
}
