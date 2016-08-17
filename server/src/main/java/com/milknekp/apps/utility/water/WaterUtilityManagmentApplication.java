package com.milknekp.apps.utility.water;

import com.milknekp.apps.utility.water.model.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.InputStream;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

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
        /*
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
        */
        List<Customer> customers = customerRepository.findAll();
        if(customers.isEmpty())
        {
            System.out.println("DB empty fill with default data");

            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            Workbook actaEntregaWorkbook = WorkbookFactory.create(classloader.getResourceAsStream("static/ACTA_ENTREGA.xlsx"));
            int numberOfSheets = actaEntregaWorkbook.getNumberOfSheets();

            for(int i = 0; i < numberOfSheets; i++)
            {
                Sheet sheet = actaEntregaWorkbook.getSheetAt(i);
                String zoneName = sheet.getSheetName();

                Zone zone = new Zone(zoneName);
                zoneRepository.save(zone);

                int rows = sheet.getPhysicalNumberOfRows();
                Row row = null;
                int j;
                for(j = 0; j < rows; j++)
                {
                    row = sheet.getRow(j);

                    if(row != null && row.getCell(0) != null)
                    {
                        row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                        if(row.getCell(0).getStringCellValue().trim().equals("1"))
                        break;
                    }
                }

                for(; j < rows; j++)
                {
                    row = sheet.getRow(j);
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    String codigo = row.getCell(1).getStringCellValue().trim();
                    if(codigo == null || codigo.trim().equals(""))
                    {
                        break;
                    }

                    Register register = new Register(codigo, 0f, 0f);
                    registerRepository.save(register);

                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);

                    String customerName = row.getCell(2).getStringCellValue().trim().toUpperCase();
                    String customerDocumentID = row.getCell(3).getStringCellValue().trim();
                    String connectionAddress = row.getCell(4).getStringCellValue().trim().toUpperCase();
                    String connectionComment = row.getCell(5).getStringCellValue().trim().toUpperCase();

                    Customer customer = null;
                    if(customerDocumentID != null && !customerDocumentID.equals("")
                            && customerRepository.findOneByDocumentId(customerDocumentID) != null)
                    {
                        customer = customerRepository.findOneByDocumentId(customerDocumentID);
                    }
                    else if(customerRepository.findOneByName(customerName) != null)
                    {
                        customer = customerRepository.findOneByName(customerName);
                    }
                    else
                    {
                        customer = new Customer(customerName, customerDocumentID);
                        customerRepository.save(customer);
                    }

                    Connection connection = new Connection(connectionAddress, new Date(new java.util.Date().getTime()),
                            new Date(new java.util.Date().getTime()), true, connectionComment);
                    connection.setRegister(register);
                    connection.setCustomer(customer);
                    connection.setZone(zone);
                    connectionRepository.save(connection);
                }

                System.out.println(zoneName);
            }

            actaEntregaWorkbook.close();
            System.out.println(numberOfSheets);
        }
        else
        {
            System.out.println("try to get connections");
            for (Customer customer: customers) {
                Collection<Connection> connections = customer.getConnections();
                if(connections.toArray().length > 1)
                {
                    System.out.println(customer.getName() + " " + connections.toArray().length);
                    for (Connection connection: connections) {
                        System.out.println(connection.getAddress() + " " + connection.getRegister().getRegisterName());
                    }
                }
            }
        }

    }
}
