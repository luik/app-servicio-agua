package com.milkneko.apps.utility.water.response;

import com.milkneko.apps.utility.water.model.MeasureStamp;

import java.sql.Date;

public class MeasureStampResponse {

    public static MeasureStampResponse createFrom(MeasureStamp measureStamp){
        float prevMeasureValue;
        if(measureStamp.getPrevMeasureStamp() != null){
            prevMeasureValue = measureStamp.getPrevMeasureStamp().getValue();
        }
        else{
            prevMeasureValue = measureStamp.getRegister().getInitialValue();
        }

        int seasonalConnectionDebtID = 0;
        if(measureStamp.getSeasonalConnectionDebt() != null){
            seasonalConnectionDebtID = measureStamp.getSeasonalConnectionDebt().getId();
        }

        int seasonEntryID = measureStamp.getSeasonEntry().getYear()*12 + measureStamp.getSeasonEntry().getMonth();

        return new MeasureStampResponse(measureStamp.getId(), measureStamp.getDate(), measureStamp.getValue(),
                measureStamp.getConnection().getId(), measureStamp.getRegister().getRegisterId(), seasonalConnectionDebtID,
                seasonEntryID,
                measureStamp.getConnection().getCustomer().getName(), measureStamp.getConnection().getZone().getName(),
                measureStamp.getConnection().getAddress(), measureStamp.getValue(), false,
                prevMeasureValue
        );
    }

    private int id;
    private Date date;
    private float value;
    private int connectionID;
    private String registerID;
    private int seasonalConnectionDebtID;
    private int seasonEntryID;
    private String customerName;
    private String zoneName;
    private String address;
    private float modifiedValue;
    private boolean pending;
    private float prevValue;

    public MeasureStampResponse() {
    }

    public MeasureStampResponse(int id, Date date, float value, int connectionID, String registerID, int seasonalConnectionDebtID,
                                int seasonEntryID,
                                String customerName, String zoneName, String address, float modifiedValue, boolean pending,
                                float prevValue ) {
        this.id = id;
        this.date = date;
        this.value = value;
        this.connectionID = connectionID;
        this.registerID = registerID;
        this.seasonalConnectionDebtID = seasonalConnectionDebtID;
        this.seasonEntryID = seasonEntryID;
        this.customerName = customerName;
        this.zoneName = zoneName;
        this.address = address;
        this.modifiedValue = modifiedValue;
        this.pending = pending;
        this.prevValue = prevValue;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getConnectionID() {
        return connectionID;
    }

    public void setConnectionID(int connectionID) {
        this.connectionID = connectionID;
    }

    public String getRegisterID() {
        return registerID;
    }

    public void setRegisterID(String registerID) {
        this.registerID = registerID;
    }

    public int getSeasonalConnectionDebtID() {
        return seasonalConnectionDebtID;
    }

    public void setSeasonalConnectionDebtID(int seasonalConnectionDebtID) {
        this.seasonalConnectionDebtID = seasonalConnectionDebtID;
    }

    public int getSeasonEntryID() {
        return seasonEntryID;
    }

    public void setSeasonEntryID(int seasonEntryID) {
        this.seasonEntryID = seasonEntryID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getModifiedValue() {
        return modifiedValue;
    }

    public void setModifiedValue(float modifiedValue) {
        this.modifiedValue = modifiedValue;
    }

    public boolean isPending() {
        return pending;
    }

    public float getPrevValue() {
        return prevValue;
    }

    public void setPrevValue(float prevValue) {
        this.prevValue = prevValue;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }
}
