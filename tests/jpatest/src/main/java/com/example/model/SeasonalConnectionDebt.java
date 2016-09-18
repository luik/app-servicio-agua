package com.example.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class SeasonalConnectionDebt {
    @Id
    @GeneratedValue
    private int id;
    private Date issuedDay;

    @ManyToOne
    private Connection connection;

    @OneToOne(mappedBy = "seasonalConnectionDebt")
    private SeasonalConnectionPayment seasonalConnectionPayment;

    @OneToOne
    private MeasureStamp initialMeasureStamp;
    @OneToOne
    private MeasureStamp finalMeasureStamp;

    @ManyToOne
    private SeasonEntry seasonEntry;

    public SeasonalConnectionDebt() {
    }

    public SeasonalConnectionDebt(Date issuedDay) {
        this.issuedDay = issuedDay;
    }

    public int getId(){
        return this.id;
    }

    public Date getIssuedDay() {
        return issuedDay;
    }

    public void setIssuedDay(Date issuedDay) {
        this.issuedDay = issuedDay;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public SeasonalConnectionPayment getSeasonalConnectionPayment() {
        return seasonalConnectionPayment;
    }

    public void setSeasonalConnectionPayment(SeasonalConnectionPayment seasonalConnectionPayment) {
        this.seasonalConnectionPayment = seasonalConnectionPayment;
    }

    public MeasureStamp getInitialMeasureStamp() {
        return initialMeasureStamp;
    }

    public void setInitialMeasureStamp(MeasureStamp initialMeasureStamp) {
        this.initialMeasureStamp = initialMeasureStamp;
    }

    public MeasureStamp getFinalMeasureStamp() {
        return finalMeasureStamp;
    }

    public void setFinalMeasureStamp(MeasureStamp finalMeasureStamp) {
        this.finalMeasureStamp = finalMeasureStamp;
    }

    public SeasonEntry getSeasonEntry() {
        return seasonEntry;
    }

    public void setSeasonEntry(SeasonEntry seasonEntry) {
        this.seasonEntry = seasonEntry;
    }
}
