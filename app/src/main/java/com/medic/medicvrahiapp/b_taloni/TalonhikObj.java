package com.medic.medicvrahiapp.b_taloni;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TalonhikObj {
    public String getId() {
        return id;
    }

    public Date getTalonTime() {
        return talonTime;
    }

    String id;
    Date talonTime;

    public TalonhikObj(String id, String talonTime, String gmt) {

        this.id = id;
        this.talonTime = stringToDate(talonTime, gmt);
    }
    public Date stringToDate(String time, String gmt){
        String timeGMT = time+" "+gmt;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZ");
        dateFormat.setTimeZone(TimeZone.getTimeZone(gmt));
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZ");
        Date date = null;
        try {
            date = (Date) formatter.parseObject(timeGMT);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
