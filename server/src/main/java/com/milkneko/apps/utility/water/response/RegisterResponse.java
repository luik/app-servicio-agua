package com.milkneko.apps.utility.water.response;

import com.milkneko.apps.utility.water.model.Register;

public class RegisterResponse {

    public static RegisterResponse createFromRegister(Register register){
        if(register.getConnection() != null){
            return new RegisterResponse(register.getId(), register.getRegisterId(), register.getInitialValue(),
                    register.getConnection().getId(), register.getConnection().getCustomer().getName(),
                    register.getConnection().getZone().getName(), register.getConnection().getAddress());
        }
        return new RegisterResponse(register.getId(), register.getRegisterId(), register.getInitialValue(),
                -1, "NC", "NC", "NC");
    }

    private int id;
    private String registerID;
    private float initialValue;
    private int connectionId;
    private String customerName;
    private String zoneName;
    private String address;

    public RegisterResponse() {
    }

    public RegisterResponse(int id, String registerID, float value, int connectionId,
                            String customerName, String zoneName, String address) {
        this.id = id;
        this.registerID = registerID;
        this.initialValue = value;
        this.connectionId = connectionId;
        this.customerName = customerName;
        this.zoneName = zoneName;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getRegisterID() {
        return registerID;
    }

    public void setRegisterID(String registerID) {
        this.registerID = registerID;
    }

    public float getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(float initialValue) {
        this.initialValue = initialValue;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
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
}
