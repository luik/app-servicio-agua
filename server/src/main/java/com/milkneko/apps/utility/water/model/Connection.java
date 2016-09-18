package com.milkneko.apps.utility.water.model;

import javax.persistence.*;
import javax.persistence.Entity;
import java.sql.Date;
import java.util.Collection;

@Entity
public class Connection {
    @Id
    @GeneratedValue
    private int id;
    private String address;
    private Date startDate;
    private Date endDate;
    private boolean isActive;
    @Column(length = 1000)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Zone zone;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    private Register register;

    @OneToMany(mappedBy = "connection", fetch = FetchType.LAZY)
    private Collection<MeasureStamp> measureStamps;

    @OneToMany(mappedBy = "connection", fetch = FetchType.LAZY)
    private Collection<SeasonalConnectionDebt> seasonalConnectionDebts;

    public Connection() {
    }

    public Connection(String address, Date startDate, Date endDate, boolean isActive, String comment) {
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.comment = comment;
    }

    public int getId(){
        return this.id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Collection<SeasonalConnectionDebt> getSeasonalConnectionDebts() {
        return seasonalConnectionDebts;
    }

    public void setSeasonalConnectionDebts(Collection<SeasonalConnectionDebt> seasonalConnectionDebts) {
        this.seasonalConnectionDebts = seasonalConnectionDebts;
    }

    public Collection<MeasureStamp> getMeasureStamps() {
        return measureStamps;
    }

    public void setMeasureStamps(Collection<MeasureStamp> measureStamps) {
        this.measureStamps = measureStamps;
    }
}
