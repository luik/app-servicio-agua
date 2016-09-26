package com.milkneko.apps.utility.water.model;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Collection;

@Entity
public class Register {
    @Id
    @GeneratedValue
    private int id;
    private String registerId;
    private float initialValue;

    @OneToOne(mappedBy = "register", fetch = FetchType.LAZY)
    private Connection connection;

    @OneToMany(mappedBy = "register", fetch = FetchType.LAZY)
    private Collection<MeasureStamp> measureStamps;
	@OneToOne
	private MeasureStamp lastMeasureStamp;

    public Register(){
    }

    public Register(String registerId, float initialValue) {
        this.registerId = registerId;
        this.initialValue = initialValue;
    }

    public int getId(){
        return this.id;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public float getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(float initialValue) {
        this.initialValue = initialValue;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Collection<MeasureStamp> getMeasureStamps() {
        return measureStamps;
    }

    public void setMeasureStamps(Collection<MeasureStamp> measureStamps) {
        this.measureStamps = measureStamps;
    }

	public MeasureStamp getLastMeasureStamp() {
	    return lastMeasureStamp;
	}

	public void setLastMeasureStamp(MeasureStamp measureStamp) {
        if(this.lastMeasureStamp != null && this.lastMeasureStamp.getDate().compareTo(measureStamp.getDate()) >= 0){
            throw new VerifyError("Register: Previous measure stamp date must be lower than its date");
        }

	    this.lastMeasureStamp = measureStamp;
	}
}
