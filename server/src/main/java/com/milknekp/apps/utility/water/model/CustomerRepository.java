package com.milknekp.apps.utility.water.model;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findOneByName(String name);
    Customer findOneByDocumentId(String documentID);
}
