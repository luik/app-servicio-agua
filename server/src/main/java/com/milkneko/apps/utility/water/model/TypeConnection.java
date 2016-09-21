package com.milkneko.apps.utility.water.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class TypeConnection {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String limits;
    private String pricesM3;
    private String pricesDrain;
    private float fixedCharge;
    private float connectionCharge;

    @OneToMany(mappedBy = "typeConnection", fetch = FetchType.LAZY)
    private Collection<Connection> connections;

    public TypeConnection() {
    }

    public TypeConnection(int id, String name, String limits, String pricesM3, String pricesDrain, float fixedCharge, float connectionCharge) {
        this.id = id;
        this.name = name;
        this.limits = limits;
        this.pricesM3 = pricesM3;
        this.pricesDrain = pricesDrain;
        this.fixedCharge = fixedCharge;
        this.connectionCharge = connectionCharge;
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

    public String getLimits() {
        return limits;
    }

    public void setLimits(String limits) {
        this.limits = limits;
    }

    public String getPricesM3() {
        return pricesM3;
    }

    public void setPricesM3(String pricesM3) {
        this.pricesM3 = pricesM3;
    }

    public String getPricesDrain() {
        return pricesDrain;
    }

    public void setPricesDrain(String pricesDrain) {
        this.pricesDrain = pricesDrain;
    }

    public float getFixedCharge() {
        return fixedCharge;
    }

    public void setFixedCharge(float fixedCharge) {
        this.fixedCharge = fixedCharge;
    }

    public float getConnectionCharge() {
        return connectionCharge;
    }

    public void setConnectionCharge(float connectionCharge) {
        this.connectionCharge = connectionCharge;
    }

    /*
    Generated
    * */

    public float getPriceM3Of(float value){
        float[] limits = getValuesFrom(getLimits());
        float[] prices = getValuesFrom(getPricesM3());

        int idx;

        for(idx = 0; idx < limits.length; idx++){
            if(value < limits[idx]){
                return prices[idx];
            }
        }

        return prices[prices.length - 1];
    }

    public float getPriceDrainOf(float value){
        float[] limits = getValuesFrom(getLimits());
        float[] prices = getValuesFrom(getPricesDrain());

        int idx;

        for(idx = 0; idx < limits.length; idx++){
            if(value < limits[idx]){
                return prices[idx];
            }
        }

        return prices[prices.length - 1];
    }

    private float[] getValuesFrom(String data){
        if(data.equals("")){
            return new float[0];
        }

        String[] dataArray = data.split(";");
        float[] prices = new float[dataArray.length];

        for (int i = 0; i < dataArray.length; i++) {
            prices[i] = Float.parseFloat(dataArray[i]);
        }

        return prices;
    }

}
