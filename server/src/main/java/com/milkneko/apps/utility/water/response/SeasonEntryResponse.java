package com.milkneko.apps.utility.water.response;

import com.milkneko.apps.utility.water.util.SeasonsUtil;

public class SeasonEntryResponse {
    private int id;
    private int year;
    private int month;
    private String monthName;

    public SeasonEntryResponse() {
    }

    public SeasonEntryResponse(int year, int month) {
        this.year = year;
        this.month = month;
        this.monthName = SeasonsUtil.getMonthName(month);
        this.id = (year - 2016)*12 + month;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public int getId(){
        return id;
    }
}
