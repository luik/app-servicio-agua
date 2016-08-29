package com.milkneko.apps.utility.water.response;

public class ZoneResponse {
    private int id;
    private String name;

    public ZoneResponse() {
    }

    public ZoneResponse(int id, String name) {
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
