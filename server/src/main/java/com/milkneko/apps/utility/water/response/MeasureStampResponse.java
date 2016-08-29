package com.milkneko.apps.utility.water.response;

import java.sql.Date;

public class MeasureStampResponse {
    private int id;
    private Date date;
    private float value;
    private int connectionID;
    private String registerID;

    public MeasureStampResponse() {
    }

    public MeasureStampResponse(Date date, float value, int connectionID, String registerID) {
        this.date = date;
        this.value = value;
        this.connectionID = connectionID;
        this.registerID = registerID;
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
}
