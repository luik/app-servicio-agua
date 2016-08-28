package com.milknekp.apps.utility.water;

import com.milknekp.apps.utility.water.model.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Component
public class DataInitializer{

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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


    public void initialize()throws Exception{
        List<Customer> customers = customerRepository.findAll();
        if(customers.isEmpty())
        {
            System.out.println("DB empty fill with default data");
            initializeExcelData();
        }

        System.out.println("try to get connections");
        testData();

        initializeTestRegisters();
    }

    private void initializeTestRegisters(){
        for(int i=0; i<100; i++)
        {
            String registerName = Integer.toString((int)(Math.random()*100000 + 7000000));
            Register register = new Register(registerName, 0f, 0f);
            registerRepository.save(register);
        }
    }

    private void testData(){
        List<Customer> customers = customerRepository.findAll();
        for (Customer customer: customers) {
            Collection<Connection> connections = customer.getConnections();
            if(connections.toArray().length > 1)
            {
                System.out.println(customer.getName() + " " + connections.toArray().length);
                for (Connection connection: connections) {
                    System.out.println(connection.getAddress() + " " + connection.getRegister().getRegisterId());
                }
            }
        }
    }

    private void initializeExcelData() throws Exception{
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

                Connection connection = new Connection(connectionAddress, new Date(this.dateFormat.parse("2016-01-15").getTime()),
                        null, true, connectionComment);
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
}
