package com.milkneko.apps.utility.water.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Config {
    public static String INTEREST_RATE_PENALTY = "Tasa interés mora";
    public static String MONTHS_TO_DUE_DEBT = "Meses vencimiento recibo";
    public static String MONTHS_TO_CUT_SERVICE = "Meses corte servicio";

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String value;

    public Config() {
    }

    public Config(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public int getId(){
        return this.id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // derived

    public int getIntValue(){
        return Integer.parseInt(value);
    }

    public float getFloatValue(){
        return Float.parseFloat(value);
    }

}
