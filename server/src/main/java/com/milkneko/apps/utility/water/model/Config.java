package com.milkneko.apps.utility.water.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Config {
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
