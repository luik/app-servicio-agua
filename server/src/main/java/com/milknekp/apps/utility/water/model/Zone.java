package com.milknekp.apps.utility.water.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Zone {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    @OneToMany
    @PrimaryKeyJoinColumn
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

    public void setName(String lastName) {
        this.name = name;
    }

}
