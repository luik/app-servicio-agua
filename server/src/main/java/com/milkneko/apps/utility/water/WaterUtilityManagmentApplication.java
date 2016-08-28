package com.milkneko.apps.utility.water;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WaterUtilityManagmentApplication implements CommandLineRunner{

    @Autowired
    private DataInitializer dataInitializer;

	public static void main(String[] args) {
		SpringApplication.run(WaterUtilityManagmentApplication.class, args);
	}

    @Override
    public void run(String... strings) throws Exception {
        dataInitializer.initialize();
    }
}
