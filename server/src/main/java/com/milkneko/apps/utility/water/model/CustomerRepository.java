package com.milkneko.apps.utility.water.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findOneByName(String name);
    Customer findOneByDocumentId(String documentID);

    @Query("SELECT c FROM Customer c JOIN FETCH c.connections")
    List<Customer> findAllAndFetchConnectionsEagerly();
}
