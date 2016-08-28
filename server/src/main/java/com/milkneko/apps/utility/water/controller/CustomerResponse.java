package com.milkneko.apps.utility.water.controller;

public class CustomerResponse {
    private int id;
    private String name;
    private String documentId;

    public CustomerResponse() {
    }

    public CustomerResponse(int id, String name, String documentId) {
        this.id = id;
        this.name = name;
        this.documentId = documentId;
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

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
