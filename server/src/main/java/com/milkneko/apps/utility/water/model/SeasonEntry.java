package com.milkneko.apps.utility.water.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class SeasonEntry implements Serializable{
    @EmbeddedId
    private SeasonEntryKey id;
    private float priceM3;

    @OneToMany(mappedBy = "seasonEntry", fetch = FetchType.EAGER)
    private Collection<SeasonalConnectionDebt> seasonalConnectionDebts;

    public SeasonEntry() {
    }

    public SeasonEntry(int year, int month, float priceM3) {
        this.id = new SeasonEntryKey(year, month);
        this.priceM3 = priceM3;
    }

    public int getYear() {
        return this.id.getYear();
    }

    public int getMonth() {
        return this.id.getMonth();
    }

    public float getPriceM3() {
        return priceM3;
    }

    public void setPriceM3(float priceM3) {
        this.priceM3 = priceM3;
    }

    public Collection<SeasonalConnectionDebt> getSeasonalConnectionDebts() {
        return seasonalConnectionDebts;
    }

    public void setSeasonalConnectionDebts(Collection<SeasonalConnectionDebt> seasonalConnectionDebts) {
        this.seasonalConnectionDebts = seasonalConnectionDebts;
    }
}

@Embeddable
class SeasonEntryKey implements Serializable
{
    private int year;
    private int month;

    public SeasonEntryKey(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}