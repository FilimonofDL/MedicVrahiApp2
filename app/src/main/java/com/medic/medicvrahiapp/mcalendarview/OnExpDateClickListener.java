package com.medic.medicvrahiapp.mcalendarview;

import android.view.View;

import com.medic.medicvrahiapp.mcalendarview.base_cell_view.base_mark_view.default_mark_view.DefaultMarkView;
import com.medic.medicvrahiapp.mcalendarview.base_cell_view.default_cell_view.DefaultCellView;


/**
 * Created by Bigflower on 2015/12/10.
 * <p>
 * Мне приходится иметь дело в последний раз и на этот раз отдельно.
   * И сегодня и в другие дни разные, поэтому есть два суждения
   * 1. Судя по последнему клику
   * 2. Судя по этому клику
 */
public class OnExpDateClickListener extends OnDateClickListener {

    private View lastClickedView;
    private View lastClickedMarkedView;
    private DateData lastClickedDate = CurrentCalendar.getCurrentDateData();

    @Override
    public void onDateClick(View view, DateData date) {

        if(view instanceof DefaultCellView) {

           // Определить последний клик
            if (lastClickedView != null) {
                ubratSObihnogoMarku(view);
            }else {
                System.out.println("lastclickedview == null");
            }
            // Судите по этому кликуv
            ((DefaultCellView) view).setDateChoose();
            lastClickedView = view;
            lastClickedDate = date;
        }else if(view instanceof DefaultMarkView){
            if(lastClickedView!=null){
//                ((DefaultCellView) lastClickedView).setDateNormal();
                ubratSObihnogoMarku(view);
            }
            if(lastClickedMarkedView!=null){
                ((DefaultMarkView)lastClickedMarkedView).setTvNotBold();
            }
            System.out.println("cell  default mark view " + view.toString());
            ((DefaultMarkView) view).setTvBold();
            lastClickedMarkedView=view;
        }


    }
    void ubratSObihnogoMarku(View view){

        System.out.println("lastclickedview !=null");
        // Сохранить!
        if (lastClickedView == view)
            return;
        if (lastClickedDate.equals(CurrentCalendar.getCurrentDateData())) {
            ((DefaultCellView) lastClickedView).setDateToday();
            System.out.println("normal if date click3");
        } else {
            ((DefaultCellView) lastClickedView).setDateNormal();
            System.out.println("normal else date click");
        }
    }

}