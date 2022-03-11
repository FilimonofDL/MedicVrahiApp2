package com.medic.medicvrahiapp.mcalendarview.base_cell_view.base_mark_view.default_mark_view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.medic.medicvrahiapp.R;
import com.medic.medicvrahiapp.mcalendarview.CellConfig;
import com.medic.medicvrahiapp.mcalendarview.DayData;
import com.medic.medicvrahiapp.mcalendarview.MarkStyle;
import com.medic.medicvrahiapp.mcalendarview.base_cell_view.base_mark_view.BaseMarkView;


/**
 * Created by bob.sun on 15/8/28.
 */
public class DefaultMarkView extends BaseMarkView {
    private TextView textView;
    private AbsListView.LayoutParams matchParentParams;
    private int orientation;

    private View sideBar;
    private TextView markTextView;
    private ShapeDrawable circleDrawable;

    public DefaultMarkView(Context context) {
        super(context);
    }

   public void setTvBold(){
        textView.setTypeface(null, Typeface.BOLD);
    }
   public void setTvNotBold(){
        textView.setTypeface(null, Typeface.NORMAL);
    }

    public void setEmptyCircleDraw(){

    }

    public DefaultMarkView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initLayoutWithStyle(MarkStyle style){
        textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        matchParentParams = new AbsListView.LayoutParams((int) CellConfig.cellWidth, (int) CellConfig.cellHeight);
        switch (style.getStyle()){
            case MarkStyle.DEFAULT:
                this.setLayoutParams(matchParentParams);
                this.setOrientation(HORIZONTAL);
                textView.setTextColor(Color.WHITE);
                circleDrawable = new ShapeDrawable(new OvalShape());
                circleDrawable.getPaint().setColor(style.getColor());
                this.setPadding(20, 20, 20, 20);
                textView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, (float) 1.0));
                textView.setBackground(circleDrawable);
                this.addView(textView);
                return;
            case MarkStyle.BACKGROUND:
                this.setLayoutParams(matchParentParams);
                this.setOrientation(HORIZONTAL);
                textView.setTextColor(Color.WHITE);
                circleDrawable = new ShapeDrawable(new OvalShape());
                circleDrawable.getPaint().setColor(style.getColor());
                this.setPadding(20, 20, 20, 20);
                textView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, (float) 1.0));
                textView.setBackground(circleDrawable);
                this.addView(textView);
                return;
            case MarkStyle.BACKGROUND_CIRCLE_EMPTY:
                this.setLayoutParams(matchParentParams);
                this.setOrientation(HORIZONTAL);
                textView.setTextColor(style.getColor());
                circleDrawable = new ShapeDrawable(new OvalShape());
                circleDrawable.getPaint().setColor(style.getColor());
                this.setPadding(20, 20, 20, 20);
                textView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, (float) 1.0));

                Drawable drawable = getResources().getDrawable(R.drawable.krug_today_day);
//                drawable.setTint(Color.rgb(0,255,0));


//                LayerDrawable layerDrawable = (LayerDrawable) getResources()
//                        .getDrawable(R.drawable.krug_today_day);
//                GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.itemId);
//                int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.5f, getResources().getDisplayMetrics());
//
//                gradientDrawable.setStroke(px, getResources().getColor(R.color.colorPrimary));




//                ShapeDrawable drawableS = new ShapeDrawable(new OvalShape());
//                drawableS.getPaint().setColor(Color.GRAY);
////                drawableS.getPaint().setStyle(Style.STROKE);
//                drawableS.getPaint().setStrokeWidth(50);
//                drawableS.getPaint().setAntiAlias(true);






                textView.setBackground(drawable);
                this.addView(textView);
                return;
            case MarkStyle.DOT:
                this.setLayoutParams(matchParentParams);
                this.setOrientation(VERTICAL);
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, (float) 2.0));

                this.addView(new PlaceHolderVertical(getContext()));
                this.addView(textView);
                this.addView(new Dot(getContext(), style.getColor()));
                return;
            case MarkStyle.RIGHTSIDEBAR:
                this.setLayoutParams(matchParentParams);
                this.setOrientation(HORIZONTAL);
                textView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, (float) 3.0));

                this.addView(new PlaceHolderHorizontal(getContext()));
                this.addView(textView);
                PlaceHolderHorizontal barRight = new PlaceHolderHorizontal(getContext());
                barRight.setBackgroundColor(style.getColor());
                this.addView(barRight);
                return;
            case MarkStyle.LEFTSIDEBAR:
                this.setLayoutParams(matchParentParams);
                this.setOrientation(HORIZONTAL);
                textView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, (float) 3.0));

                PlaceHolderHorizontal barLeft = new PlaceHolderHorizontal(getContext());
                barLeft.setBackgroundColor(style.getColor());
                this.addView(barLeft);
                this.addView(textView);
                this.addView(new PlaceHolderHorizontal(getContext()));

                return;
            default:
                throw new IllegalArgumentException("Invalid Mark Style Configuration!");
        }
    }

    @Override
    public void setDisplayText(DayData day) {
        initLayoutWithStyle(day.getDate().getMarkStyle());
        textView.setText(day.getText());
    }

    class PlaceHolderHorizontal extends View {

        LinearLayout.LayoutParams params;
        public PlaceHolderHorizontal(Context context) {
            super(context);
            params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, (float) 1.0);
            this.setLayoutParams(params);
        }

        public PlaceHolderHorizontal(Context context, AttributeSet attrs) {
            super(context, attrs);
            params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, (float) 1.0);
            this.setLayoutParams(params);
        }
    }

    class PlaceHolderVertical extends View {

        LinearLayout.LayoutParams params;
        public PlaceHolderVertical(Context context) {
            super(context);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, (float) 1.0);
            this.setLayoutParams(params);
        }

        public PlaceHolderVertical(Context context, AttributeSet attrs) {
            super(context, attrs);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, (float) 1.0);
            this.setLayoutParams(params);
        }
    }

    class Dot extends RelativeLayout {

        public Dot(Context context, int color) {
            super(context);
            this.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, (float) 1.0));
            View dotView = new View(getContext());
            LayoutParams lp = new LayoutParams(10, 10);
            lp.addRule(CENTER_IN_PARENT,TRUE);
            dotView.setLayoutParams(lp);
            ShapeDrawable dot = new ShapeDrawable(new OvalShape());

            dot.getPaint().setColor(color);
            dotView.setBackground(dot);
            this.addView(dotView);
        }
    }
}
