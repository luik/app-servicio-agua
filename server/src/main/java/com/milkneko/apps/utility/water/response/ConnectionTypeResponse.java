package com.milkneko.apps.utility.water.response;

import com.milkneko.apps.utility.water.model.ConnectionType;

import java.util.ArrayList;
import java.util.List;

public class ConnectionTypeResponse {

    public static List<ConnectionTypeResponse> createFrom(ConnectionType connectionType){
        String[] limits = connectionType.getLimits().split(";");
        String[] waterServicePrices = connectionType.getPricesM3().split(";");
        String[] drainServicePrices = connectionType.getPricesDrain().split(";");
        if(connectionType.getLimits().equals("")){
            limits = new String[0];
        }

        List<ConnectionTypeResponse> connectionTypeResponses = new ArrayList<>();

        for(int i = 0; i <= limits.length; i++){
            String limitString = "0 A MÁS";

            if(limits.length > 0) {
                limitString = "0 A " + (Integer.parseInt(limits[limits.length - 1]) - 1);
                if (i != 0) {
                    if (i < limits.length) {
                        limitString = limits[i - 1] + " A " + (Integer.parseInt(limits[i]) - 1);
                    } else {
                        limitString = limits[i - 1] + " A MÁS";
                    }
                }
            }

            connectionTypeResponses.add(new ConnectionTypeResponse(connectionType.getId(), i, connectionType.getName(), limitString ,
                    Float.parseFloat(waterServicePrices[i]), Float.parseFloat(drainServicePrices[i]), connectionType.getFixedCharge(),
                    connectionType.getConnectionCharge(), connectionType.getConnectionChargeDuration()));
        }

        return connectionTypeResponses;
    }

    private int id;
    private int idx;
    private String category;
    private String consumptionRange;
    private float waterServicePrice;
    private float drainServicePrice;
    private float fixedCharge;
    private float connectionCharge;
    private int connectionChargeDuration;

    public ConnectionTypeResponse() {
    }

    public ConnectionTypeResponse(int id, int idx, String category, String consumptionRange, float waterServicePrice,
                                  float drainServicePrice, float fixedCharge, float connectionCharge, int connectionChargeDuration) {
        this.id = id;
        this.idx = idx;
        this.category = category;
        this.consumptionRange = consumptionRange;
        this.waterServicePrice = waterServicePrice;
        this.drainServicePrice = drainServicePrice;
        this.fixedCharge = fixedCharge;
        this.connectionCharge = connectionCharge;
        this.connectionChargeDuration = connectionChargeDuration;
    }

    public int getId() {
        return id;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getConsumptionRange() {
        return consumptionRange;
    }

    public void setConsumptionRange(String consumptionRange) {
        this.consumptionRange = consumptionRange;
    }

    public float getWaterServicePrice() {
        return waterServicePrice;
    }

    public void setWaterServicePrice(float waterServicePrice) {
        this.waterServicePrice = waterServicePrice;
    }

    public float getDrainServicePrice() {
        return drainServicePrice;
    }

    public void setDrainServicePrice(float drainServicePrice) {
        this.drainServicePrice = drainServicePrice;
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

    public int getConnectionChargeDuration() {
        return connectionChargeDuration;
    }

    public void setConnectionChargeDuration(int connectionChargeDuration) {
        this.connectionChargeDuration = connectionChargeDuration;
    }
}
