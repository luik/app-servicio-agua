package com.milkneko.apps.utility.water.response;

import com.milkneko.apps.utility.water.model.SeasonalConnectionDebt;

import java.sql.Date;

public class SeasonalConnectionDebtResponse {

    public static SeasonalConnectionDebtResponse createFrom(SeasonalConnectionDebt seasonalConnectionDebt) {

        float seasonalConnectionDebtInitialMeasurementValue = seasonalConnectionDebt.getConnection().getRegister().getInitialValue();
        seasonalConnectionDebtInitialMeasurementValue = seasonalConnectionDebt.getMeasureStamp().getPrevMeasureStamp().getValue();

        float m3price = seasonalConnectionDebt.getConnection().getConnectionType().getPriceM3Of(
                seasonalConnectionDebt.getMeasureStamp().getValue() - seasonalConnectionDebtInitialMeasurementValue
        );

        return new SeasonalConnectionDebtResponse(seasonalConnectionDebt.getId(), seasonalConnectionDebt.getConnection().getId(),
                seasonalConnectionDebt.getIssuedDay(), seasonalConnectionDebt.getDueDate(),
                seasonalConnectionDebt.getMeasureStamp().getPrevMeasureStamp().getDate(), seasonalConnectionDebtInitialMeasurementValue,
                seasonalConnectionDebt.getMeasureStamp().getDate(),  seasonalConnectionDebt.getMeasureStamp().getValue(),
                seasonalConnectionDebt.getSeasonEntry().getYear(), seasonalConnectionDebt.getSeasonEntry().getMonth(),
                m3price, seasonalConnectionDebt.getSeasonalConnectionPayment() != null ? seasonalConnectionDebt.getSeasonalConnectionPayment().getId() : -1,
                seasonalConnectionDebt.getSeasonalConnectionPayment() != null ? seasonalConnectionDebt.getSeasonalConnectionPayment().getDate() : null,
                seasonalConnectionDebt.getConnection().getConnectionType().getPriceM3Of(seasonalConnectionDebt.getMeasureStamp().getValue() - seasonalConnectionDebt.getMeasureStamp().getPrevMeasureStamp().getValue()),
                seasonalConnectionDebt.getConnection().getConnectionType().getPriceDrainOf(seasonalConnectionDebt.getMeasureStamp().getValue() - seasonalConnectionDebt.getMeasureStamp().getPrevMeasureStamp().getValue()),
                seasonalConnectionDebt.getConnection().getConnectionType().getFixedCharge(),
                seasonalConnectionDebt.getConnection().getConnectionType().getConnectionCharge());
    }

    private int id;
    private int seasonalConnectionPaymentId;
    private Date seasonalConnectionPaymentDate;
    private int connectionId;
    private Date issuedDate;
    private Date dueDate;
    private Date initialMeasurementDate;
    private float initialMeasurementValue;
    private Date finalMeasurementDate;
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

    public SeasonalConnectionDebtResponse(int id, int connectionId, Date issuedDate, Date dueDate,
                                          Date initialMeasurementDate, float initialMeasurementValue,
                                          Date finalMeasurementDate, float finalMeasurementValue, int seasonYear, int seasonMonth, float priceM3,
                                          int seasonalConnectionPaymentId, Date seasonalConnectionPaymentDate,
                                          float waterServicePrice, float drainPrice,
                                          float fixedCharge, float connectionCharge) {
        this.id = id;
        this.connectionId = connectionId;
        this.issuedDate = issuedDate;
        this.dueDate = dueDate;
        this.initialMeasurementDate = initialMeasurementDate;
        this.initialMeasurementValue = initialMeasurementValue;
        this.finalMeasurementDate = finalMeasurementDate;
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getInitialMeasurementDate() {
        return initialMeasurementDate;
    }

    public void setInitialMeasurementDate(Date initialMeasurementDate) {
        this.initialMeasurementDate = initialMeasurementDate;
    }

    public float getInitialMeasurementValue() {
        return initialMeasurementValue;
    }

    public void setInitialMeasurementValue(float initialMeasurementValue) {
        this.initialMeasurementValue = initialMeasurementValue;
    }

    public Date getFinalMeasurementDate() {
        return finalMeasurementDate;
    }

    public void setFinalMeasurementDate(Date finalMeasurementDate) {
        this.finalMeasurementDate = finalMeasurementDate;
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
    public float getDeltaMeasurements() {
        return finalMeasurementValue - initialMeasurementValue;
    }

    public float getWaterServiceDebt() {
        return getDeltaMeasurements() * this.waterServicePrice;
    }

    public float getDebtValue() {
        return getWaterServiceDebt() + getDrainPrice() + getFixedCharge() + getConnectionCharge();
    }

    public float getTotalDebtValue() {
        return getDebtValue() + getIGVDebtValue();
    }

    public float getTotalDebtRoundedValue() {
        return getTotalDebtValue() + getRoundValue();
    }

    public float getRoundValue() {
        if (Math.IEEEremainder(getTotalDebtValue(), 0.05) > 0) {
            return -(float) Math.IEEEremainder(getTotalDebtValue(), 0.05);
        }
        return -(float) Math.IEEEremainder(getTotalDebtValue(), 0.05) - 0.05f;
    }

    public float getIGVDebtValue() {
        return 0.18f * getDebtValue();
    }

    public boolean isPaidOut() {
        return this.seasonalConnectionPaymentId != -1;
    }


}
