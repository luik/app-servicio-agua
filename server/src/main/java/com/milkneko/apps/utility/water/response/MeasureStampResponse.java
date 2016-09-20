package com.milkneko.apps.utility.water.response;

import java.sql.Date;

public class MeasureStampResponse {
    private int id;
    private Date date;
    private float value;
    private int connectionID;
    private String registerID;
    private int previousSeasonalConnectionDebtId;
    private int currentSeasonalConnectionDebtId;
    private String customerName;
    private String zoneName;
    private String address;
    private float modifiedValue;
    private boolean pending;
    private float prevValue;

    public MeasureStampResponse() {
    }

    public MeasureStampResponse(int id, Date date, float value, int connectionID, String registerID,
    		                    int previousSeasonalConnectionDebtId, int currentSeasonalConnectionDebtId,
                                String customerName, String zoneName, String address, float modifiedValue, boolean pending,
                                float prevValue ) {
        this.id = id;
        this.date = date;
        this.value = value;
        this.connectionID = connectionID;
        this.registerID = registerID;
        this.previousSeasonalConnectionDebtId = previousSeasonalConnectionDebtId;
        this.currentSeasonalConnectionDebtId = currentSeasonalConnectionDebtId;
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

    public int getPreviousSeasonalConnectionDebtId() {
        return previousSeasonalConnectionDebtId;
    }

    public int getCurrentSeasonalConnectionDebtId() {
        return currentSeasonalConnectionDebtId;
    }
}
