package com.milkneko.apps.utility.water.response;

import java.sql.Date;

public class SeasonalConnectionDebtResponse {

    private int id;
    private int seasonalConnectionPaymentId;
    private Date seasonalConnectionPaymentDate;
    private int connectionId;
    private Date issuedDate;
    private float initialMeasurementValue;
    private float finalMeasurementValue;
    private int seasonYear;
    private int seasonMonth;
    private float priceM3;
    private float waterServicePrice;
    private float drainPrice;
    private float fixedCharge;
    private float connectionCharge;

    public SeasonalConnectionDebtResponse() {
    }

    public SeasonalConnectionDebtResponse(int id, int connectionId, Date issuedDate, float initialMeasurementValue,
                                          float finalMeasurementValue, int seasonYear, int seasonMonth, float priceM3,
                                          int seasonalConnectionPaymentId, Date seasonalConnectionPaymentDate,
                                          float waterServicePrice, float drainPrice,
                                          float fixedCharge, float connectionCharge) {
        this.id = id;
    	this.connectionId = connectionId;
        this.issuedDate = issuedDate;
        this.initialMeasurementValue = initialMeasurementValue;
        this.finalMeasurementValue = finalMeasurementValue;
        this.seasonYear = seasonYear;
        this.seasonMonth = seasonMonth;
        this.priceM3 = priceM3;
        this.seasonalConnectionPaymentId = seasonalConnectionPaymentId;
        this.seasonalConnectionPaymentDate = seasonalConnectionPaymentDate;
        this.waterServicePrice = waterServicePrice;
        this.drainPrice = drainPrice;
        this.fixedCharge = fixedCharge;
        this.connectionCharge = connectionCharge;
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

    public int getSeasonalConnectionPaymentId() {
        return seasonalConnectionPaymentId;
    }

    public void setSeasonalConnectionPaymentId(int seasonalConnectionPaymentId) {
        this.seasonalConnectionPaymentId = seasonalConnectionPaymentId;
    }

    public Date getSeasonalConnectionPaymentDate() {
        return seasonalConnectionPaymentDate;
    }

    public void setSeasonalConnectionPaymentDate(Date seasonalConnectionPaymentDate) {
        this.seasonalConnectionPaymentDate = seasonalConnectionPaymentDate;
    }

    public float getWaterServicePrice() {
        return waterServicePrice;
    }

    public void setWaterServicePrice(float waterServicePrice) {
        this.waterServicePrice = waterServicePrice;
    }

    public float getDrainPrice() {
        return drainPrice;
    }

    public void setDrainPrice(float drainPrice) {
        this.drainPrice = drainPrice;
    }

    public float getFixedCharge() {
        return fixedCharge;
    }

    public void setFixedCharge(float fixedCharge) {
        this.fixedCharge = fixedCharge;
    }

    public float getConnectionCharge() {
        return connectionCharge;
    }

    public void setConnectionCharge(float connectionCharge) {
        this.connectionCharge = connectionCharge;
    }

    /*
            //derived
            private float deltaMeasurements;
            private float debtValue;
            private float totalDebtValue
            private boolean paidOut
        */
    public float getDeltaMeasurements(){
        return finalMeasurementValue - initialMeasurementValue;
    }

    public float getWaterServiceDebt()
    {
        return getDeltaMeasurements()*this.waterServicePrice;
    }

    public float getDebtValue(){
        return getWaterServiceDebt() + getDrainPrice() + getFixedCharge() + getConnectionCharge();
    }

    public float getTotalDebtValue(){
        return getDebtValue() + getIGVDebtValue();
    }

    public float getTotalDebtRoundedValue(){
        return getTotalDebtValue() + getRoundValue();
    }

    public float getRoundValue(){
    	if(Math.IEEEremainder(getTotalDebtValue(), 0.05) > 0){
    		return -(float) Math.IEEEremainder(getTotalDebtValue(), 0.05);
    	}
        return -(float) Math.IEEEremainder(getTotalDebtValue(), 0.05) - 0.05f;
    }

    public float getIGVDebtValue(){
        return 0.18f*getDebtValue();
    }

    public boolean isPaidOut(){
        return this.seasonalConnectionPaymentId != -1;
    }


}
