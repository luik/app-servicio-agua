package com.milknekp.apps.utility.water.model;

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

    @ManyToOne
    private Zone zone;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Register register;

    @OneToMany
    @PrimaryKeyJoinColumn
    private Collection<MonthExpense> monthExpenses;

    public Connection() {
    }

    public Connection(String address, Date startDate, Date endDate, boolean isActive) {
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
    }

    public int getId(){
        return this.id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String lastName) {
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

    public Collection<MonthExpense> getMonthExpenses() {
        return monthExpenses;
    }

    public void setMonthExpenses(Collection<MonthExpense> monthExpenses) {
        this.monthExpenses = monthExpenses;
    }
}
