package com.milkneko.apps.utility.water.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class ServiceShutOff implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	private Date executedDate;
	private Date date;
	private float debt;
	private Date debtDate;

	@OneToOne(mappedBy = "serviceShutOff", fetch = FetchType.LAZY)
	private Connection connection;

	@OneToOne(mappedBy = "serviceShutOff", fetch = FetchType.LAZY)
	private ServiceShutOn serviceShutOn;

	@OneToOne
	private SeasonalConnectionDebt seasonalConnectionDebt;

	public ServiceShutOff() {
	}

	public ServiceShutOff(Date executedDate, Date date, float debt, Date debtDate) {
		this.executedDate = executedDate;
		this.date = date;
		this.debt = debt;
		this.debtDate = debtDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection param) {
		this.connection = param;
	}

	public ServiceShutOn getServiceShutOn() {
		return serviceShutOn;
	}

	public void setServiceShutOn(ServiceShutOn param) {
		this.serviceShutOn = param;
	}

	public Date getExecutedDate() {
		return executedDate;
	}

	public void setExecutedDate(Date param) {
		this.executedDate = param;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date param) {
		this.date = param;
	}

	public float getDebt() {
		return debt;
	}

	public void setDebt(float param) {
		this.debt = param;
	}

	public Date getDebtDate() {
		return debtDate;
	}

	public void setDebtDate(Date param) {
		this.debtDate = param;
	}

	public SeasonalConnectionDebt getSeasonalConnectionDebt() {
	    return seasonalConnectionDebt;
	}

	public void setSeasonalConnectionDebt(SeasonalConnectionDebt param) {
	    this.seasonalConnectionDebt = param;
	}

}