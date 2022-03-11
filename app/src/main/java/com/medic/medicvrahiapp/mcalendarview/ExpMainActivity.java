package com.medic.medicvrahiapp.mcalendarview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.medic.medicvrahiapp.R;

import java.util.Calendar;


public class ExpMainActivity extends AppCompatActivity {

    private TextView YearMonthTv;
    private ExpCalendarView expCalendarView;
    private DateData selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view_exp_main_activity);

//      Get instance.
        expCalendarView = ((ExpCalendarView) findViewById(R.id.calendar_exp));
        YearMonthTv = (TextView) findViewById(R.id.main_YYMM_Tv);
        DateData dateDataNow=new DateData(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH)-2,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        YearMonthTv.setText(dateDataNow.getMonthString()+dateDataNow.getYear());

//      Set up listeners.
        expCalendarView.setOnDateClickListener(new OnExpDateClickListener()).setOnMonthScrollListener(new OnMonthScrollListener() {
            @Override
            public void onMonthChange(int year, int month) {
//                System.out.println("onMonthChange");
//                YearMonthTv.setText(String.format("%d day %d day2 ", year, month));

            }

            @Override
            public void onMonthChangeString(int year, String monthStr) {
                YearMonthTv.setText(monthStr+" "+ Integer.toString(year));
            }

            @Override
            public void onMonthScroll(float positionOffset) {
//                System.out.println("onMonthScroll");
//                Log.i("listener", "onMonthScroll:" + positionOffset);
            }
        });

        expCalendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                System.out.println("expCalendarView.setOnDateClickListener");
                expCalendarView.getMarkedDates().removeAdd();
                expCalendarView.markDate(date);
                selectedDate = date;
            }
        });

        imageInit();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2018,8,8);
        selectedDate = new DateData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
        expCalendarView.markDate(selectedDate);
    }

    private boolean ifExpand = true;

    private void imageInit() {
        final ImageView expandIV = (ImageView) findViewById(R.id.main_expandIV);
        expandIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifExpand) {
                    CellConfig.Month2WeekPos = CellConfig.middlePosition;
                    CellConfig.ifMonth = false;
//                    expandIV.setImageResource(R.mipmap.icon_arrow_down);
//                    CellConfig.weekAnchorPointDate = selectedDate;
                    expCalendarView.shrink();
                } else {
                    CellConfig.Week2MonthPos = CellConfig.middlePosition;
                    CellConfig.ifMonth = true;
//                    expandIV.setImageResource(R.mipmap.icon_arrow_up);
                    expCalendarView.expand();
                }
                ifExpand = !ifExpand;
            }
        });
    }

    public void TravelToClick(View v) {

        DateData date=new DateData(2018, 2, 15);

        expCalendarView.travelTo(date);

        System.out.println(date.toString()+" DAteData");
    }
}
