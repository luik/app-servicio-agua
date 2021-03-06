package com.milkneko.apps.utility.water.model;

import javax.persistence.*;
import javax.persistence.Entity;
import java.sql.Date;
import java.util.Collection;

@Entity
public class Connection {
    @Id
    @GeneratedValue
    private int id;
    private String address;
    private Date startDate;
    private Date endDate;
    private boolean isActive;
    @Column(length = 1000)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Zone zone;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    private Register register;

    @ManyToOne(fetch = FetchType.LAZY)
    private ConnectionType connectionType;

    @OneToMany(mappedBy = "connection", fetch = FetchType.LAZY)
    private Collection<MeasureStamp> measureStamps;

    @OneToMany(mappedBy = "connection", fetch = FetchType.LAZY)
    private Collection<SeasonalConnectionDebt> seasonalConnectionDebts;
	@OneToOne
	private ServiceShutOff serviceShutOff;
	@OneToOne(fetch =  FetchType.LAZY)
	private SeasonalConnectionPayment lastSeasonalConnectionPayment;

	@OneToOne(fetch = FetchType.LAZY)
	private SeasonalConnectionDebt lastSeasonalConnectionDebt;

    public Connection() {
    }

    public Connection(String address, Date startDate, Date endDate, boolean isActive, String comment) {
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.comment = comment;
    }

    public int getId(){
        return this.id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Collection<SeasonalConnectionDebt> getSeasonalConnectionDebts() {
        return seasonalConnectionDebts;
    }

    public void setSeasonalConnectionDebts(Collection<SeasonalConnectionDebt> seasonalConnectionDebts) {
        this.seasonalConnectionDebts = seasonalConnectionDebts;
    }

    public Collection<MeasureStamp> getMeasureStamps() {
        return measureStamps;
    }

    public void setMeasureStamps(Collection<MeasureStamp> measureStamps) {
        this.measureStamps = measureStamps;
    }

	public ServiceShutOff getServiceShutOff() {
	    return serviceShutOff;
	}

	public void setServiceShutOff(ServiceShutOff param) {
	    this.serviceShutOff = param;
	}

	public SeasonalConnectionPayment getLastSeasonalConnectionPayment() {
	    return lastSeasonalConnectionPayment;
	}

	public void setLastSeasonalConnectionPayment(SeasonalConnectionPayment seasonalConnectionPayment) {
        if(lastSeasonalConnectionPayment != null && lastSeasonalConnectionPayment.getDate().compareTo(seasonalConnectionPayment.getDate()) > 0){
            throw new VerifyError("Connection: Last seasonal connection payment date must be upper than the current");
        }

	    this.lastSeasonalConnectionPayment = seasonalConnectionPayment;
	}

	public SeasonalConnectionDebt getLastSeasonalConnectionDebt() {
	    return lastSeasonalConnectionDebt;
	}

	public void setLastSeasonalConnectionDebt(SeasonalConnectionDebt seasonalConnectionDebt) {
        if(lastSeasonalConnectionDebt != null && lastSeasonalConnectionDebt.getIssuedDay().compareTo(seasonalConnectionDebt.getIssuedDay()) > 0){
            throw new VerifyError("Connection: Last seasonal connection debt date must be upper than the current");
        }

	    this.lastSeasonalConnectionDebt = seasonalConnectionDebt;
	}
}
