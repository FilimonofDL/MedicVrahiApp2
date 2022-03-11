package com.medic.medicvrahiapp.b_taloni;

public class SozdatTalonhikiInfoObject {
    private int hour = 10;
    private int minutes = 0;
    private int intervalMin = 40;
    private int kolihestvo = 1;

    public SozdatTalonhikiInfoObject() {
    }

    public SozdatTalonhikiInfoObject(int hour, int minutes, int intervalMin, int kolihestvo) {

        this.hour = hour;
        this.minutes = minutes;
        this.intervalMin = intervalMin;
        this.kolihestvo = kolihestvo;
    }

    public int getHour() {

        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getIntervalMin() {
        return intervalMin;
    }

    public void setIntervalMin(int intervalMin) {
        this.intervalMin = intervalMin;
    }

    public int getKolihestvo() {
        return kolihestvo;
    }

    public void setKolihestvo(int kolihestvo) {
        this.kolihestvo = kolihestvo;
    }
}
