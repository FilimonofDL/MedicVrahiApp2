package com.medic.medicvrahiapp.mcalendarview;

import android.view.View;

/**
 * Created by bob.sun on 15/8/28.
 */
public abstract class OnDateClickListener {
    public static OnDateClickListener instance;

    public abstract void onDateClick(View view, DateData date);
}
