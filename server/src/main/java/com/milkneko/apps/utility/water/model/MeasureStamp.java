package com.milkneko.apps.utility.water.model;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(indexes = {
        @Index(name = "index_date", columnList = "date")
})
public class MeasureStamp {
    @Id
    @GeneratedValue
    private int id;
    private Date date;
    private float value;

    @ManyToOne(fetch = FetchType.LAZY)
    private Register register;
    @ManyToOne(fetch = FetchType.LAZY)
    private Connection connection;

    @OneToOne(mappedBy = "measureStamp", fetch = FetchType.LAZY)
    private SeasonalConnectionDebt seasonalConnectionDebt;

    @OneToOne
	private MeasureStamp prevMeasureStamp;
	@ManyToOne
	private SeasonEntry seasonEntry;

    public MeasureStamp() {
    }

    public MeasureStamp(Date date, float value) {
        this.date = date;
        this.value = value;
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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public SeasonalConnectionDebt getSeasonalConnectionDebt() {
        return seasonalConnectionDebt;
    }

    public void setSeasonalConnectionDebt(SeasonalConnectionDebt currentSeasonalConnectionDebt) {
        this.seasonalConnectionDebt = currentSeasonalConnectionDebt;
    }

	public MeasureStamp getPrevMeasureStamp() {
	    return prevMeasureStamp;
	}

	public void setPrevMeasureStamp(MeasureStamp param) {
	    this.prevMeasureStamp = param;
	}

	public SeasonEntry getSeasonEntry() {
	    return seasonEntry;
	}

	public void setSeasonEntry(SeasonEntry param) {
	    this.seasonEntry = param;
	}
}
