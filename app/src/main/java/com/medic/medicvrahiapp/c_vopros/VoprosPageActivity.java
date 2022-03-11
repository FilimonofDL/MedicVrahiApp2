package com.medic.medicvrahiapp.c_vopros;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.medic.medicvrahiapp.R;
import com.medic.medicvrahiapp.model.InitOsnovnActivityBottomButton;

public class VoprosPageActivity extends InitOsnovnActivityBottomButton {

        int DIALOG_TIME = 1;
        int myHour = 14;
        int myMinute = 35;
        TextView tvTime;

        /** Called when the activity is first created. */
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_vopros_page);
            tvTime = (TextView) findViewById(R.id.tvVremiaTalonhika);
        }

        public void onclick(View view) {
            showDialog(DIALOG_TIME);
        }

        protected Dialog onCreateDialog(int id) {
            if (id == DIALOG_TIME) {
                TimePickerDialog tpd = new TimePickerDialog(this, myCallBack, myHour, myMinute, true);
                return tpd;
            }
            return super.onCreateDialog(id);
        }

        OnTimeSetListener myCallBack = new OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myHour = hourOfDay;
                myMinute = minute;
                tvTime.setText("Time is " + myHour + " hours " + myMinute + " minutes");
            }
        };
    }