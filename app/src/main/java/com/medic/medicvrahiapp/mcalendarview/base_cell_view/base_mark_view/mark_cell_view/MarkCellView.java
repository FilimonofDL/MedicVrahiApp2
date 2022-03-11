package com.medic.medicvrahiapp.mcalendarview.base_cell_view.base_mark_view.mark_cell_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.medic.medicvrahiapp.R;
import com.medic.medicvrahiapp.mcalendarview.DayData;
import com.medic.medicvrahiapp.mcalendarview.MarkStyleExp;
import com.medic.medicvrahiapp.mcalendarview.base_cell_view.base_mark_view.BaseMarkView;


/**
 * Created by bob.sun on 15/8/30.
 */
public class MarkCellView extends BaseMarkView {
    public MarkCellView(Context context) {
        super(context);
    }

    public MarkCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setDisplayText(DayData day) {
        ((TextView) this.findViewById(R.id.id_cell_text)).setText(day.getText());
    }
    public void setDateToday(){
        setBackgroundDrawable(MarkStyleExp.today);
//        textView.setTextColor(getResources().getColor(R.color.day_today));
    }
}
