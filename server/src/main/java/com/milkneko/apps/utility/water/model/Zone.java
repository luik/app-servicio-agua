package com.milkneko.apps.utility.water.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Zone {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    @OneToMany(mappedBy = "zone", fetch = FetchType.LAZY)
    private Collection<Connection> connections;

    public Zone() {
    }

    public Zone(String name) {
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
