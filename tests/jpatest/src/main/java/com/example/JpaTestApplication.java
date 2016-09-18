package com.example;

import com.example.model.Connection;
import com.example.model.Customer;
import com.example.model.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class JpaTestApplication implements CommandLineRunner{

    @Autowired
    DataInitializer dataInitializer;

    @Autowired
    CustomerRepository customerRepository;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(JpaTestApplication.class, args);
	}

    @Override
    public void run(String... strings) throws Exception {
        //dataInitializer.initialize();
        testGetConnections();
    }

    private void testGetConnections(){
        Instant instant;

        instant = Instant.now();

        List<String> names = new ArrayList<>();

        List<Customer> customers = customerRepository.findAll();
        for (Customer customer :
                customers) {
            Collection<Connection> connections = customer.getConnections();
            if(connections.size() > 1){
                //System.out.println(customer.getName());
                names.add(customer.getName());
            }
        }
        System.out.println(" : " + Duration.between(instant, Instant.now()));
    }
}
