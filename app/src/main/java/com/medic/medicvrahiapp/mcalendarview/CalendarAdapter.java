package com.medic.medicvrahiapp.mcalendarview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import com.medic.medicvrahiapp.mcalendarview.base_cell_view.BaseCellView;
import com.medic.medicvrahiapp.mcalendarview.base_cell_view.base_mark_view.BaseMarkView;
import com.medic.medicvrahiapp.mcalendarview.base_cell_view.base_mark_view.default_mark_view.DefaultMarkView;
import com.medic.medicvrahiapp.mcalendarview.base_cell_view.default_cell_view.DefaultCellView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by bob.sun on 15/8/27.
 */
public class CalendarAdapter extends ArrayAdapter implements Observer {
    private ArrayList data;
    private int cellView = -1;
    private int markView = -1;
    public CalendarAdapter(Context context, int resource, ArrayList data) {
        super(context, resource);
        this.data = data;
        MarkedDates.getInstance().addObserver(this);
    }

    public CalendarAdapter setCellViews(int cellView, int markView){
        this.cellView = cellView;
        this.markView = markView;
        return this;
    }


    public View getView(int position, View convertView, ViewGroup viewGroup){
        System.out.println("get view");
        View ret = null;
//        DayData dayData = (DayData) new DayData(new DateData(2018, 7,2));
        DayData dayData = (DayData) data.get(position);
        MarkStyle style = MarkedDates.getInstance().check(dayData.getDate());
        boolean marked = style != null;
        if (marked){
            dayData.getDate().setMarkStyle(style);
            if (markView > 0){
                BaseMarkView baseMarkView = (BaseMarkView) View.inflate(getContext(), markView, null);
                baseMarkView.setDisplayText(dayData);
                ret = baseMarkView;
            } else {
                ret = new DefaultMarkView(getContext());
                ((DefaultMarkView) ret).setDisplayText(dayData);
            }
        } else {
            if (cellView > 0) {
                BaseCellView baseCellView = (BaseCellView) View.inflate(getContext(), cellView, null);
                baseCellView.setDisplayText(dayData);
                ret = baseCellView;
            } else {
                ret = new DefaultCellView(getContext());
                ((DefaultCellView) ret).setDisplayText(dayData);
            }
        }
        ((BaseCellView) ret).setDate(dayData.getDate());
        if (OnDateClickListener.instance != null) {
            System.out.println("imst interf");
            ((BaseCellView) ret).setOnDateClickListener(OnDateClickListener.instance);
        }else {
            System.out.println(" else imst interf");
        }
        if (dayData.getDate().equals(CurrentCalendar.getCurrentDateData())){
            ret.setBackground(MarkStyle.todayBackground);
        }
        return ret;
    }

    @Override
    public int getCount(){
        return data.size();
    }

    @Override
    public void update(Observable observable, Object data) {
        this.notifyDataSetChanged();
    }
}
