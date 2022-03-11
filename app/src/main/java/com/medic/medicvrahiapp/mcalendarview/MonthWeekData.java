package com.medic.medicvrahiapp.mcalendarview;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthWeekData {
    private DateData pointDate;
    private Calendar calendar = Calendar.getInstance();

    private int realPosition;
    private int weekIndex, preNumber, afterNumber;

    private ArrayList<DayData> monthContent;
    private ArrayList<DayData> weekContent;

    public MonthWeekData(int position) {
//        System.out.println(position +" MonthWeekData " + Integer.toString(calendar.get(Calendar.MONTH) + 1));
        realPosition = position;
        calendar = Calendar.getInstance();
        DateData today = new DateData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        if (CellConfig.m2wPointDate == null) {
            CellConfig.m2wPointDate = today;
        }
        if (CellConfig.w2mPointDate == null) {
            CellConfig.w2mPointDate = today;
        }
        if (CellConfig.weekAnchorPointDate == null) {
            CellConfig.weekAnchorPointDate = today;
        }

        if (CellConfig.ifMonth) {
//            System.out.println("Init arr mont");
            getPointDate();
            initMonthArray();
        } else {
            initWeekArray();
        }
    }

    private void getPointDate() {
        // получаем заказанную точку
        calendar.set(CellConfig.w2mPointDate.getYear(),
                CellConfig.w2mPointDate.getMonth() - 1, CellConfig.w2mPointDate.getDay());
        // получаем относительную разницу в смещении страниц
        int distance = CellConfig.Week2MonthPos - CellConfig.Month2WeekPos;
//        System.out.println(Integer.toString(distance)+" dist, Week2MonthPos " +
//                CellConfig.Week2MonthPos + " Month2WeekPos " +
//                CellConfig.Month2WeekPos);
        calendar.add(Calendar.DATE, distance * 7);
//        System.out.println("Месяц изменился ? " + CellConfig.w2mPointDate.getMonthString());
//        System.out.println("getPointDate  базовая дата: " +
//                Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)+1) + "." +
//                (calendar.get(Calendar.MONTH) + 1));
        // Определяем , будет ли средняя страница
//        System.out.println("if real Posit "+realPosition+" ?= "+CellConfig.middlePosition);
        if (realPosition == 500) {
//      if (realPosition == CellConfig.middlePosition) {
            CellConfig.m2wPointDate = new DateData(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
//            System.out.println(Integer.toString(calendar.get(Calendar.MONTH)+1)+" !!!!!!  выбран месяц текущий " + CellConfig.m2wPointDate.getMonthString());
        } else {
            calendar.add(Calendar.MONTH, realPosition - CellConfig.Week2MonthPos);
//            System.out.println(Integer.toString(calendar.get(Calendar.MONTH)+1)+" !!!!!! выбран месяц НEEEЕ текущий " + CellConfig.m2wPointDate.getMonthString());
        }
        calendar.set(Calendar.DATE, 1);
//        System.out.println("?????  getPointDate  после последнего вызова：" +
//                calendar.get(Calendar.DAY_OF_MONTH) +
//                "." + (calendar.get(Calendar.MONTH) + 1));

    }

    //создать массив с помесячным календарем
    private void initMonthArray() {
        DayData addDate;
        boolean vEtomMesiacePervoeHisloVVoskresenie = false;
        boolean vEtomMesiacePervoeHisloVVtornik = false;
        monthContent = new ArrayList<DayData>();
        initMonthParams();
        // Добавляет TextView этого месяца
//        calendar.add(Calendar.MONTH, 1);
        int thisMonthDaysNumberTotal = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        System.out.println("Mаксимальный день в месяце текущем "+Integer.toString(calendar.get(Calendar.MONTH)+1)+" "
//                +Integer.toString(thisMonthDaysNumberTotal)+" ");
        int indexVstavlenogoDniaOtPervogoHisla = 0;
        if (calendar.get(Calendar.DAY_OF_WEEK) == 1) vEtomMesiacePervoeHisloVVoskresenie = true;
        if (calendar.get(Calendar.DAY_OF_WEEK) == 2) vEtomMesiacePervoeHisloVVtornik = true;
//        System.out.println("Create arr month " + Integer.toString(calendar.get(Calendar.MONTH) + 1));

        //заполняет в массив временное значение для предыдущего месяца
        if (!vEtomMesiacePervoeHisloVVoskresenie) {
            System.out.println("IF  ! vEtomMesiacePervoeHisloVVoskresenie"+Integer.toString(calendar.get(Calendar.MONTH)+1));
            indexVstavlenogoDniaOtPervogoHisla= calendar.get(Calendar.DAY_OF_WEEK) - 3 ;
            for (int x = 0; x < calendar.get(Calendar.DAY_OF_WEEK) - 2; x++) {
                //чтобы создать места в массиве предыдущего месяца,
                // вставляем временные значения в ячейки предыдущего месяца
                monthContent.add(new DayData(new DateData(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), 1)));
                System.out.println("Add date to massiv "+1);


            }
        } else {
            System.out.println("NOOT IF  ! vEtomMesiacePervoeHisloVVoskresenie"+Integer.toString(calendar.get(Calendar.MONTH)+1));
            indexVstavlenogoDniaOtPervogoHisla = 5;
            for (int x = 0; x < 6; x++) {
                //чтобы создать места в массиве предыдущего месяца,
                // вставляем временные значения в ячейки предыдущего месяца
                monthContent.add(new DayData(new DateData(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), 1)));
            }
        }
        System.out.println("indexVstavlenogoDniaOtPervogoHisla = "+indexVstavlenogoDniaOtPervogoHisla);

//заполняет даты в массив числа текущего месяца
        for (int day = 1; day < thisMonthDaysNumberTotal + 1; day++) {
            addDate = new DayData(new DateData(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), day));
            addDate.setTextColor(Color.BLACK);
//            addDate.setTextColor(Color.BLUE);
            monthContent.add(addDate);
//                System.out.println("if Calendar.DAY_OF_WEEK "+Integer.toString(calendar.get(Calendar.MONTH)+1)+"m "
//                        +Integer.toString(calendar.get(Calendar.DAY_OF_WEEK)));

        }
        // Добавляет TextView предыдущего месяца
        calendar.add(Calendar.MONTH, -1);
        int lastMonthDayNumberPreMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //if для того, что-бы не вставлять в понедельник последнее число предыдущего месяца
        //вместо первого
        if(!vEtomMesiacePervoeHisloVVtornik) {
            for (int preDay = lastMonthDayNumberPreMonth; indexVstavlenogoDniaOtPervogoHisla >= 0;
                 indexVstavlenogoDniaOtPervogoHisla--) {

                addDate = new DayData(new DateData(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), preDay));
                addDate.setTextColor(Color.LTGRAY);
                monthContent.set(indexVstavlenogoDniaOtPervogoHisla, addDate);
                preDay--;
            }
        }

        // Добавляет TextView следующего месяца
        calendar.add(Calendar.MONTH, 2);
        for (int afterDay = 1; monthContent.size() < 42; afterDay++) {
            addDate = new DayData(new DateData(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), afterDay));
            addDate.setTextColor(Color.LTGRAY);
            monthContent.add(addDate);
        }
    }

    //создать массив с понедельным календарем
    private void initWeekArray() {
        weekContent = new ArrayList<DayData>();

        // Здесь: место, где была получена последняя запись, а опорная точка страницы
        // определяется в соответствии с перемещением номера страницы
        // (страница составляет несколько месяцев)
        calendar.set(CellConfig.m2wPointDate.getYear(), CellConfig.m2wPointDate.getMonth() - 1, CellConfig.m2wPointDate.getDay());
        if (CellConfig.Week2MonthPos != CellConfig.Month2WeekPos) {
            // Разница между средней страницей и сегодняшним относительным номером страницы
            int distance = CellConfig.Month2WeekPos - CellConfig.Week2MonthPos;
            // Прокрутите страницу до текущей страницы
            calendar.add(Calendar.MONTH, distance);
        }
        // Если это сегодняшний месяц, дата привязки сегодня,
        // если это не сегодняшний месяц, дата привязки равна 1
        calendar.set(Calendar.DAY_OF_MONTH, getAnchorDayOfMonth(CellConfig.weekAnchorPointDate));

        // Вот: после получения страницы якорь, содержимое трех дисплея определяется,
        // и по обе стороны от среднего отображения различных страниц
        if (realPosition == CellConfig.Month2WeekPos) {

        } else {
            calendar.add(Calendar.DATE, (realPosition - CellConfig.Month2WeekPos) * 7);
        }

        // Запишите точку на средней странице pointDate
        if (realPosition == CellConfig.middlePosition) {
            CellConfig.w2mPointDate = new DateData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
            System.out.println("if real pos текущий：" + CellConfig.w2mPointDate.toString());
        }

        // Добавить данные
        DayData addDate;
        weekIndex = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, -weekIndex + 1);
        for (int i = 0; i < 7; i++) {
            addDate = new DayData(new DateData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)));
            weekContent.add(addDate);
            calendar.add(Calendar.DATE, 1);
        }
    }

    //для календаря понедельного
    private int getAnchorDayOfMonth(DateData date) {
        int thisMonth = Calendar.getInstance().get(Calendar.MONTH);
        int month = date.getMonth() - 1;
        int selectedMonth = calendar.get(Calendar.MONTH);
        if (selectedMonth == month && calendar.get(Calendar.YEAR) == date.getYear()) {
            System.out.println("getAnchorDayOfMonth retern "+date.getDay());
            return date.getDay();
        }
        if (selectedMonth == thisMonth && month != thisMonth) {
            Calendar calendar = Calendar.getInstance();
            System.out.println("getAnchorDayOfMonth retern "+calendar.get(Calendar.DAY_OF_MONTH));
            return calendar.get(Calendar.DAY_OF_MONTH);
        }

        System.out.println("getAnchorDayOfMonth retern 1");
        return 1;
    }

    //понять понедельный либо помесячный вид
    public ArrayList getData() {
        if (CellConfig.ifMonth)
            return monthContent;
        else
            return weekContent;
    }

    //определяет старт и финиш дат месяца в массиве, что бы знать куда их воткнуть ,
    // если вставлять первым текущий месяц
    private void initMonthParams() {
        weekIndex = calendar.get(Calendar.DAY_OF_WEEK);
//        System.out.println("initMonthParams "+"Calendar.DAY_OF_WEEK "+Integer.toString(Calendar.DAY_OF_WEEK));
        //подгонка на ноябрь, бред TODO
//        if (calendar.get(Calendar.MONTH) == 11)
//            weekIndex--;
        preNumber = weekIndex - 1;
//        System.out.println("initMonthParams "+"preNumber "+Integer.toString(preNumber));
        afterNumber = 42 - calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - preNumber;
//        System.out.println("initMonthParams "+"preNumber "+Integer.toString(afterNumber));
    }
}
