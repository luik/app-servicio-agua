package com.milkneko.apps.utility.water.controller;

public class RegisterResponse {
    private int id;
    private String registerID;
    private float value;

    public RegisterResponse() {
    }

    public RegisterResponse(int id, String registerID, float value) {
        this.id = id;
        this.registerID = registerID;
        this.value = value;
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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
