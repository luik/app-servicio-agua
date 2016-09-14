package com.milkneko.apps.utility.water.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class SeasonalConnectionPayment {
    @Id
    @GeneratedValue
    private int id;
    private Date date;

    @OneToOne
    private SeasonalConnectionDebt seasonalConnectionDebt;

    public SeasonalConnectionPayment() {
    }

    public SeasonalConnectionPayment(Date date) {
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

    public SeasonalConnectionDebt getSeasonalConnectionDebt() {
        return seasonalConnectionDebt;
    }

    public void setSeasonalConnectionDebt(SeasonalConnectionDebt seasonalConnectionDebt) {
        this.seasonalConnectionDebt = seasonalConnectionDebt;
    }
}
