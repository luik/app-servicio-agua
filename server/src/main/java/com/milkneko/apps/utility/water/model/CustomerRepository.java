package com.milkneko.apps.utility.water.model;


import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findOneByName(String name);
    Customer findOneByDocumentId(String documentID);
}
