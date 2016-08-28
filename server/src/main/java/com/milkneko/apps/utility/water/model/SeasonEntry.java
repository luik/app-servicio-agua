package com.milkneko.apps.utility.water.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class SeasonEntry {
    @Id
    @GeneratedValue
    private int id;
    private float priceM3;

    @OneToMany(mappedBy = "seasonEntry", fetch = FetchType.EAGER)
    private Collection<SeasonalConnectionDebt> seasonalConnectionDebts;

    public SeasonEntry() {
    }

    public SeasonEntry(float priceM3) {
        this.priceM3 = priceM3;
    }

    public int getId(){
        return this.id;
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
