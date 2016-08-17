package com.milknekp.apps.utility.water.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class MonthExpense {
    @Id
    @GeneratedValue
    private int id;
    private int season;
    private float registerValue;
    private float diffRegisterValue;
    private Date issuedDay;
    private float priceCubicMeter;

    @ManyToOne
    private Connection connection;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Payment payment;

    public MonthExpense() {
    }

    public MonthExpense(int season, float registerValue, float diffRegisterValue, Date issuedDay, float priceCubicMeter) {
        this.season = season;
        this.registerValue = registerValue;
        this.diffRegisterValue = diffRegisterValue;
        this.issuedDay = issuedDay;
        this.priceCubicMeter = priceCubicMeter;
    }

    public int getId(){
        return this.id;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public float getDiffRegisterValue() {
        return diffRegisterValue;
    }

    public void setDiffRegisterValue(float diffRegisterValue) {
        this.diffRegisterValue = diffRegisterValue;
    }

    public float getRegisterValue() {
        return registerValue;
    }

    public void setRegisterValue(float registerValue) {
        this.registerValue = registerValue;
    }

    public Date getIssuedDay() {
        return issuedDay;
    }

    public void setIssuedDay(Date issuedDay) {
        this.issuedDay = issuedDay;
    }

    public float getPriceCubicMeter() {
        return priceCubicMeter;
    }

    public void setPriceCubicMeter(float priceCubicMeter) {
        this.priceCubicMeter = priceCubicMeter;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
