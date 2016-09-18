package com.milkneko.apps.utility.water.model;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(indexes = {
        @Index(name = "index_date", columnList = "date")
})
public class MeasureStamp {
    @Id
    @GeneratedValue
    private int id;
    private Date date;
    private float value;

    @ManyToOne(fetch = FetchType.LAZY)
    private Register register;
    @ManyToOne(fetch = FetchType.LAZY)
    private Connection connection;

    @OneToOne(mappedBy = "initialMeasureStamp", fetch = FetchType.LAZY)
    private SeasonalConnectionDebt prevSeasonalConnectionDebt;
    @OneToOne(mappedBy = "finalMeasureStamp", fetch = FetchType.LAZY)
    private SeasonalConnectionDebt currentSeasonalConnectionDebt;

    public MeasureStamp() {
    }

    public MeasureStamp(Date date, float value) {
        this.date = date;
        this.value = value;
    }

    public int getId(){
        return this.id;
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

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public SeasonalConnectionDebt getPrevSeasonalConnectionDebt() {
        return prevSeasonalConnectionDebt;
    }

    public void setPrevSeasonalConnectionDebt(SeasonalConnectionDebt prevSeasonalConnectionDebt) {
        this.prevSeasonalConnectionDebt = prevSeasonalConnectionDebt;
    }

    public SeasonalConnectionDebt getCurrentSeasonalConnectionDebt() {
        return currentSeasonalConnectionDebt;
    }

    public void setCurrentSeasonalConnectionDebt(SeasonalConnectionDebt currentSeasonalConnectionDebt) {
        this.currentSeasonalConnectionDebt = currentSeasonalConnectionDebt;
    }
}
