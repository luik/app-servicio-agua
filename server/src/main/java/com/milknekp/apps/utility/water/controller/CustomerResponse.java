package com.milknekp.apps.utility.water.controller;

public class CustomerResponse {
    private String name;
    private String documentId;

    public CustomerResponse() {
    }

    public CustomerResponse(String name, String documentId) {
        this.name = name;
        this.documentId = documentId;
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
