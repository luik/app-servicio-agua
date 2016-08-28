package com.milkneko.apps.utility.water.controller;

import java.sql.Date;

public class ConnectionResponse {
    private int id;
    private int customerID;
    private String customerName;
    private int zoneID;
    private String zoneName;
    private int registerID;
    private String registerName;
    private String address;
    private Date startDate;
    private Date endDate;
    private boolean isActive;
    private String comment;

    public ConnectionResponse() {
    }

    public ConnectionResponse(int id, int customerID, String customerName, int zoneID, String zoneName,
                              int registerID, String registerName, String address, Date startDate, Date endDate,
                               boolean isActive, String comment  ) {
        this.id = id;
        this.customerID = customerID;
        this.customerName = customerName;
        this.zoneID = zoneID;
        this.zoneName = zoneName;
        this.registerID = registerID;
        this.registerName = registerName;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.comment = comment;
    }

    public int getId() {
        return id;
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

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getZoneID() {
        return zoneID;
    }

    public void setZoneID(int zoneID) {
        this.zoneID = zoneID;
    }

    public int getRegisterID() {
        return registerID;
    }

    public void setRegisterID(int registerID) {
        this.registerID = registerID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
