package com.milkneko.apps.utility.water.response;

public class SeasonEntryResponse {
    private int id;
    private int year;
    private int month;
    private float priceM3;

    public SeasonEntryResponse() {
    }

    public SeasonEntryResponse(int year, int month, float priceM3) {
        this.year = year;
        this.month = month;
        this.priceM3 = priceM3;
        this.id = (year - 2016)*12 + month;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public float getPriceM3() {
        return priceM3;
    }

    public void setPriceM3(float priceM3) {
        this.priceM3 = priceM3;
    }

    public int getId(){
        return id;
    }
}
