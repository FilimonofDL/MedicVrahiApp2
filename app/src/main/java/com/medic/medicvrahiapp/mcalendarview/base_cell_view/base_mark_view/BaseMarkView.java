package com.medic.medicvrahiapp.mcalendarview.base_cell_view.base_mark_view;

import android.content.Context;
import android.util.AttributeSet;

import com.medic.medicvrahiapp.mcalendarview.DateData;
import com.medic.medicvrahiapp.mcalendarview.OnDateClickListener;
import com.medic.medicvrahiapp.mcalendarview.base_cell_view.BaseCellView;


/**
 * Created by bob.sun on 15/8/28.
 */
public abstract class BaseMarkView extends BaseCellView {
    private OnDateClickListener clickListener;
    private DateData date;

    public BaseMarkView(Context context) {
        super(context);
    }

    public BaseMarkView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
