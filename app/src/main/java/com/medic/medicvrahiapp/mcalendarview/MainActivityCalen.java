package com.medic.medicvrahiapp.mcalendarview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.medic.medicvrahiapp.R;


public class MainActivityCalen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_calen);
//      Get instance.
        final MCalendarView calendarView = ((MCalendarView) findViewById(R.id.calendar));


//************************************************************************************************************
//        Use default view.
//        If you want to use customized cells, un-comment below line and modify `DateCellView`, `MarkCellView`.
//************************************************************************************************************

//        calendarView.setDateCell(R.layout.layout_date_cell).setMarkedCell(R.layout.layout_mark_cell);

    }

//**********************************************************
//  Generated codes, didn't modified, so you can ignore them.
//**********************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, ExpMainActivity.class));
            return true;
        } else if(id == R.id.action_tab_act) {
            startActivity(new Intent(this, TabActivity.class));
            return true ;
        }

        return super.onOptionsItemSelected(item);
    }
}
