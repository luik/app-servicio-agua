package com.milknekp.apps.utility.water;

import com.milknekp.apps.utility.water.model.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WaterUtilityManagmentApplication {

    @Autowired
	private CustomerRepository customerRepository;

	public static void main(String[] args) {
		SpringApplication.run(WaterUtilityManagmentApplication.class, args);
	}

}
