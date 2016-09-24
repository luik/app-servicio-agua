package com.milkneko.apps.utility.water.response;

import com.milkneko.apps.utility.water.model.Config;

public class ConfigResponse {

    public static ConfigResponse createFrom(Config config){
        return new ConfigResponse(config.getId(), config.getName(), config.getValue());
    }

    private int id;
    private String name;
    private String value;

    public ConfigResponse() {
    }

    public ConfigResponse(int id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
