package com.medic.medicvrahiapp.mcalendarview.base_cell_view.date_cell_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.medic.medicvrahiapp.R;
import com.medic.medicvrahiapp.mcalendarview.DayData;
import com.medic.medicvrahiapp.mcalendarview.base_cell_view.BaseCellView;


/**
 * Created by bob.sun on 15/8/30.
 */
public class DateCellView extends BaseCellView {
    public DateCellView(Context context) {
        super(context);
    }

    public DateCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setDisplayText(DayData day) {
        ((TextView) this.findViewById(R.id.id_cell_text)).setText(day.getText());
    }
}
