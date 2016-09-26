package com.milkneko.apps.utility.water.response;

import java.sql.Date;

import com.milkneko.apps.utility.water.model.SeasonalConnectionDebt;

public class SeasonalConnectionPaymentResponse {

    public static SeasonalConnectionPaymentResponse createFrom(SeasonalConnectionDebt seasonalConnectionDebt){

        float m3price = seasonalConnectionDebt.getConnection().getConnectionType().getPriceM3Of(
                seasonalConnectionDebt.getMeasureStamp().getValue() - seasonalConnectionDebt.getMeasureStamp().getPrevMeasureStamp().getValue()
        );

        return new SeasonalConnectionPaymentResponse(seasonalConnectionDebt.getSeasonalConnectionPayment().getId(),
                seasonalConnectionDebt.getId(), seasonalConnectionDebt.getConnection().getId(), seasonalConnectionDebt.getIssuedDay(),
                seasonalConnectionDebt.getMeasureStamp().getPrevMeasureStamp().getValue(), seasonalConnectionDebt.getMeasureStamp().getValue(),
                seasonalConnectionDebt.getSeasonEntry().getYear(), seasonalConnectionDebt.getSeasonEntry().getMonth(),
                m3price, seasonalConnectionDebt.getSeasonalConnectionPayment().getDate());
    }

    private int id;
    private int seasonalConnectionDebtId;
    private int connectionId;
    private Date issuedDate;
    private Date paymentDate;
    private float initialMeasurementValue;
    private float finalMeasurementValue;
    private int seasonYear;
    private int seasonMonth;
    private float priceM3;

    public SeasonalConnectionPaymentResponse() {
    }

    public SeasonalConnectionPaymentResponse(int id, int seasonalConnectionDebtId, int connectionId, Date issuedDate, float initialMeasurementValue,
                                             float finalMeasurementValue, int seasonYear, int seasonMonth, float priceM3, Date paymentDate) {
        this.id = id;
        this.seasonalConnectionDebtId = seasonalConnectionDebtId;
    	this.connectionId = connectionId;
        this.issuedDate = issuedDate;
        this.paymentDate = paymentDate;
        this.initialMeasurementValue = initialMeasurementValue;
        this.finalMeasurementValue = finalMeasurementValue;
        this.seasonYear = seasonYear;
        this.seasonMonth = seasonMonth;
        this.priceM3 = priceM3;
    }

    public int getId() {
        return id;
    }

    public int getSeasonalConnectionDebtId() {
        return seasonalConnectionDebtId;
    }

    public void setSeasonalConnectionDebtId(int seasonalConnectionDebtId) {
        this.seasonalConnectionDebtId = seasonalConnectionDebtId;
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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
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

    public float getTotalDebtValue(){
        float totalDebt = 1.18f*getDebtValue();
        float round = ((Math.round(totalDebt*100))%5)*-1/100f;
        totalDebt += round;
        return totalDebt;
    }


}
