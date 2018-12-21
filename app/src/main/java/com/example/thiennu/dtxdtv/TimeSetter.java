package com.example.thiennu.dtxdtv;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class TimeSetter implements View.OnFocusChangeListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener, View.OnTouchListener {

        private EditText mEditText;
        private Calendar mCalendar;
        private SimpleDateFormat mFormat;
        private Context mContext;

        public TimeSetter(EditText editText, Context context){
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
            Log.d("Time click", "Time is clicked");
            if (view == mEditText)
                showPicker(view);
        }

        private void showPicker(View view) {
            if (mCalendar == null)
                mCalendar = Calendar.getInstance();

            int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = mCalendar.get(Calendar.MINUTE);

            new TimePickerDialog(mContext, this, hour, minute, true).show();
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCalendar.set(Calendar.MINUTE, minute);

            if (mFormat == null)
                mFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

            this.mEditText.setText(mFormat.format(mCalendar.getTime()));
        }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
            showPicker(v);
        return false;
    }
}
