package com.milkneko.apps.utility.water.response;

import com.milkneko.apps.utility.water.model.ConnectionType;

public class ConnectionCategoryResponse {

    public static ConnectionCategoryResponse createFrom(ConnectionType connectionType){
        return new ConnectionCategoryResponse(connectionType.getId(), connectionType.getName());
    }

    private int id;
    private String name;

    public ConnectionCategoryResponse() {
    }

    public ConnectionCategoryResponse(int id, String name) {
        this.id = id;
        this.name = name;
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
}
