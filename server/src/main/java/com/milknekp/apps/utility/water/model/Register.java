package com.milknekp.apps.utility.water.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

@Entity
public class Register {
    @Id
    @GeneratedValue
    private int id;
    private String registerId;
    private float value;
    private float prevValue;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Connection connection;

    public Register(){
    }

    public Register(String registerId, float value, float prevValue) {
        this.registerId = registerId;
        this.value = value;
        this.prevValue = prevValue;
    }

    public int getId(){
        return this.id;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getPrevValue() {
        return prevValue;
    }

    public void setPrevValue(float prevValue) {
        this.prevValue = prevValue;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}