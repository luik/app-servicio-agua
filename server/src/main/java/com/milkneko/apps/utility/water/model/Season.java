package com.milkneko.apps.utility.water.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Season {
    @Id
    @GeneratedValue
    private int id;
    private float priceM3;

    public Season() {
    }

    public Season(float priceM3) {
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
}
