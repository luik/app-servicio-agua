package com.milkneko.apps.utility.water.response;

import com.milkneko.apps.utility.water.model.ServiceShutOff;

import java.sql.Date;

public class ServiceShutOffResponse {
    public static ServiceShutOffResponse createFrom(ServiceShutOff serviceShutOff){
        return new ServiceShutOffResponse(
                serviceShutOff.getId(), serviceShutOff.getDate(), serviceShutOff.getDebt(), serviceShutOff.getDebtDate(),
                serviceShutOff.getExecutedDate(),
                serviceShutOff.getSeasonalConnectionDebt().getId(),
//                serviceShutOff.getConnection().getId(),
//                serviceShutOff.getConnection().getCustomer().getName(),
//                serviceShutOff.getConnection().getZone().getName(),
//                serviceShutOff.getConnection().getAddress()
                -1,
                "NAME",
                "NAME",
                "ADDRESS"
        );
    }

    private int id;
    private Date date;
    private float debt;
    private Date debtDate;
    private Date executedDate;
    private int seasonalConnectionDebtId;
    private int connectionId;
    private String customerName;
    private String zoneName;
    private String address;

    public ServiceShutOffResponse() {
    }

    public ServiceShutOffResponse(int id, Date date, float debt, Date debtDate, Date executedDate, int connectionDebtId,
                                  int connectionId, String customerName, String zoneName, String address) {
        this.id = id;
        this.date = date;
        this.debt = debt;
        this.debtDate = debtDate;
        this.executedDate = executedDate;
        this.seasonalConnectionDebtId = connectionDebtId;
        this.connectionId = connectionId;
        this.customerName = customerName;
        this.zoneName = zoneName;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getDebt() {
        return debt;
    }

    public void setDebt(float debt) {
        this.debt = debt;
    }

    public Date getDebtDate() {
        return debtDate;
    }

    public void setDebtDate(Date debtDate) {
        this.debtDate = debtDate;
    }

    public Date getExecutedDate() {
        return executedDate;
    }

    public void setExecutedDate(Date executedDate) {
        this.executedDate = executedDate;
    }

    public int getSeasonalConnectionDebtId() {
        return seasonalConnectionDebtId;
    }

    public void setSeasonalConnectionDebtId(int seasonalConnectionDebtId) {
        this.seasonalConnectionDebtId = seasonalConnectionDebtId;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
