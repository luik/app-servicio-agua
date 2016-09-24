package com.milkneko.apps.utility.water.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class SeasonalConnectionDebt {
	@Id
	@GeneratedValue
	private int id;
	private Date issuedDay;

	@ManyToOne(fetch = FetchType.LAZY)
	private Connection connection;

	@OneToOne(mappedBy = "seasonalConnectionDebt", fetch = FetchType.LAZY)
	private SeasonalConnectionPayment seasonalConnectionPayment;

	@OneToOne(fetch = FetchType.LAZY)
	private MeasureStamp initialMeasureStamp;
	@OneToOne(fetch = FetchType.LAZY)
	private MeasureStamp finalMeasureStamp;

	@ManyToOne(fetch = FetchType.LAZY)
	private SeasonEntry seasonEntry;
	private Date dueDate;

	public SeasonalConnectionDebt() {
	}

	public SeasonalConnectionDebt(Date issuedDay) {
		this.issuedDay = issuedDay;
	}

	public int getId() {
		return this.id;
	}

	public Date getIssuedDay() {
		return issuedDay;
	}

	public void setIssuedDay(Date issuedDay) {
		this.issuedDay = issuedDay;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public SeasonalConnectionPayment getSeasonalConnectionPayment() {
		return seasonalConnectionPayment;
	}

	public void setSeasonalConnectionPayment(SeasonalConnectionPayment seasonalConnectionPayment) {
		this.seasonalConnectionPayment = seasonalConnectionPayment;
	}

	public MeasureStamp getInitialMeasureStamp() {
		return initialMeasureStamp;
	}

	public void setInitialMeasureStamp(MeasureStamp initialMeasureStamp) {
		this.initialMeasureStamp = initialMeasureStamp;
	}

	public MeasureStamp getFinalMeasureStamp() {
		return finalMeasureStamp;
	}

	public void setFinalMeasureStamp(MeasureStamp finalMeasureStamp) {
		this.finalMeasureStamp = finalMeasureStamp;
	}

	public SeasonEntry getSeasonEntry() {
		return seasonEntry;
	}

	public void setSeasonEntry(SeasonEntry seasonEntry) {
		this.seasonEntry = seasonEntry;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date param) {
		this.dueDate = param;
	}
}
