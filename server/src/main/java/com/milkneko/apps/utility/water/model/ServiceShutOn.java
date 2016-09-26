package com.milkneko.apps.utility.water.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ServiceShutOn implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	@OneToOne
	private ServiceShutOff serviceShutOff;

	private Date executedDate;
	private Date date;

	public ServiceShutOn() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ServiceShutOff getServiceShutOff() {
		return serviceShutOff;
	}

	public void setServiceShutOff(ServiceShutOff param) {
		this.serviceShutOff = param;
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

}