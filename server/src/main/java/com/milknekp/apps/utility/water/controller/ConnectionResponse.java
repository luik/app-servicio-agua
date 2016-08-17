package com.milknekp.apps.utility.water.controller;

public class ConnectionResponse {
    private int id;
    private String customerName;
    private String zoneName;
    private String registerName;
    private String address;

    public ConnectionResponse() {
    }

    public ConnectionResponse(int id, String customerName, String zoneName, String registerName, String address) {
        this.id = id;
        this.customerName = customerName;
        this.zoneName = zoneName;
        this.registerName = registerName;
        this.address = address;
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
}
