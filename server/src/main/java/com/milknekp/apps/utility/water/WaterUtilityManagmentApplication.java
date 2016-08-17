package com.milknekp.apps.utility.water;

import com.milknekp.apps.utility.water.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Date;

@SpringBootApplication
public class WaterUtilityManagmentApplication implements CommandLineRunner{

    @Autowired
	private CustomerRepository customerRepository;
    @Autowired
    private RegisterRepository registerRepository;
    @Autowired
    private ZoneRepository zoneRepository;
    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private MonthExpenseRepository monthExpenseRepository;
    @Autowired
    private PaymentRepository paymentRepository;

	public static void main(String[] args) {
		SpringApplication.run(WaterUtilityManagmentApplication.class, args);
	}

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("HERE");

        Register register = new Register("1", 10, 0);
        Customer customer = new Customer("luis", "42409458");
        Zone zone = new Zone("zone1");

        registerRepository.save(register);
        customerRepository.save(customer);
        zoneRepository.save(zone);

        Connection connection = new Connection("address",
                new Date(new java.util.Date().getTime()), new Date(new java.util.Date().getTime()),true);
        connection.setRegister(register);
        connection.setCustomer(customer);
        connection.setZone(zone);

        connectionRepository.save(connection);

        MonthExpense monthExpense = new MonthExpense(2, 10, 3,
                new Date(new java.util.Date().getTime()), 0.3f);
        monthExpense.setConnection(connection);;

        monthExpenseRepository.save(monthExpense);

        Payment payment = new Payment(new Date(new java.util.Date().getTime()));
        payment.setMonthExpense(monthExpense);;

        paymentRepository.save(payment);

    }
}
