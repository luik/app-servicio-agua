package com.milkneko.apps.utility.water.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(indexes = {
    @Index(name = "index_name", columnList = "name", unique = true),
    @Index(name = "index_document_id", columnList = "documentId")
})
public class Customer {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String documentId;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Collection<Connection> connections;

    public Customer(){
    }

    public Customer(String name, String documentId) {
        this.name = name;
        this.documentId = documentId;
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

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Collection<Connection> getConnections() {
        return connections;
    }

    public void setConnections(Collection<Connection> connections) {
        this.connections = connections;
    }
}
