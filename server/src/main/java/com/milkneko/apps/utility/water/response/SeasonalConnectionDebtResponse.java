package com.milkneko.apps.utility.water.response;

import java.sql.Date;

public class SeasonalConnectionDebtResponse {
    private int id;
    private int connectionId;
    private Date issuedDate;
    private float initialMeasurementValue;
    private float finalMeasurementValue;
    private int seasonYear;
    private int seasonMonth;
    private float priceM3;

    public SeasonalConnectionDebtResponse() {
    }

    public SeasonalConnectionDebtResponse(int id, int connectionId, Date issuedDate, float initialMeasurementValue, float finalMeasurementValue, int seasonYear, int seasonMonth, float priceM3) {
        this.id = id;
    	this.connectionId = connectionId;
        this.issuedDate = issuedDate;
        this.initialMeasurementValue = initialMeasurementValue;
        this.finalMeasurementValue = finalMeasurementValue;
        this.seasonYear = seasonYear;
        this.seasonMonth = seasonMonth;
        this.priceM3 = priceM3;
    }

    public int getId() {
        return id;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public float getInitialMeasurementValue() {
        return initialMeasurementValue;
    }

    public void setInitialMeasurementValue(float initialMeasurementValue) {
        this.initialMeasurementValue = initialMeasurementValue;
    }

    public float getFinalMeasurementValue() {
        return finalMeasurementValue;
    }

    public void setFinalMeasurementValue(float finalMeasurementValue) {
        this.finalMeasurementValue = finalMeasurementValue;
    }

    public int getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(int seasonYear) {
        this.seasonYear = seasonYear;
    }

    public int getSeasonMonth() {
        return seasonMonth;
    }

    public void setSeasonMonth(int seasonMonth) {
        this.seasonMonth = seasonMonth;
    }

    public float getPriceM3() {
        return priceM3;
    }

    public void setPriceM3(float priceM3) {
        this.priceM3 = priceM3;
    }

    /*
         //derived
    private float deltaMeasurements;
    private float debtValue;
      * */
    public float getDeltaMeasurements(){
        return finalMeasurementValue - initialMeasurementValue;
    }

    public float getDebtValue(){
        return getDeltaMeasurements()*this.priceM3;
    }


}
