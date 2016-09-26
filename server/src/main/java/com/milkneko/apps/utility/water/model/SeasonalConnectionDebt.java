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
	private Date dueDate;

	@ManyToOne(fetch = FetchType.LAZY)
	private Connection connection;

	@OneToOne(mappedBy = "seasonalConnectionDebt", fetch = FetchType.LAZY)
	private SeasonalConnectionPayment seasonalConnectionPayment;

	@OneToOne(fetch = FetchType.LAZY)
	private MeasureStamp measureStamp;

	@ManyToOne(fetch = FetchType.LAZY)
	private SeasonEntry seasonEntry;

	@OneToOne
	private SeasonalConnectionDebt prevSeasonalConnectionDebt;

	@OneToOne(mappedBy = "seasonalConnectionDebt")
	private ServiceShutOff serviceShutOff;

	public SeasonalConnectionDebt() {
	}

	public SeasonalConnectionDebt(Date issuedDay, Date dueDate) {
		this.issuedDay = issuedDay;
		this.dueDate = dueDate;
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

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date param) {
		this.dueDate = param;
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

	public MeasureStamp getMeasureStamp() {
		return measureStamp;
	}

	public void setMeasureStamp(MeasureStamp finalMeasureStamp) {
		this.measureStamp = finalMeasureStamp;
	}

	public SeasonEntry getSeasonEntry() {
		return seasonEntry;
	}

	public void setSeasonEntry(SeasonEntry seasonEntry) {
		this.seasonEntry = seasonEntry;
	}

	public SeasonalConnectionDebt getPrevSeasonalConnectionDebt() {
	    return prevSeasonalConnectionDebt;
	}

	public void setPrevSeasonalConnectionDebt(SeasonalConnectionDebt seasonalConnectionDebt) {
		if(seasonalConnectionDebt.getIssuedDay().compareTo(issuedDay) >= 0){
			throw new VerifyError("SeasonalConnectionDebt: Previous seasonal connection debt date must be lower than its date");
		}

	    this.prevSeasonalConnectionDebt = seasonalConnectionDebt;
	}

	public ServiceShutOff getServiceShutOff() {
	    return serviceShutOff;
	}

	public void setServiceShutOff(ServiceShutOff param) {
	    this.serviceShutOff = param;
	}

}
