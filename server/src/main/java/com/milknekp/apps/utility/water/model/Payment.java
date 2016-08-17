package com.milknekp.apps.utility.water.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.sql.Date;

@Entity
public class Payment {
    @Id
    @GeneratedValue
    private int id;
    private Date date;

    @OneToOne
    private MonthExpense monthExpense;

    public Payment() {
    }

    public Payment(Date date) {
        this.date = date;
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

    public MonthExpense getMonthExpense() {
        return monthExpense;
    }

    public void setMonthExpense(MonthExpense monthExpense) {
        this.monthExpense = monthExpense;
    }
}
