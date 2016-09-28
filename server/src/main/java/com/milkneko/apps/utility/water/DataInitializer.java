package com.milkneko.apps.utility.water;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import com.milkneko.apps.utility.water.manager.SeasonalConnectionDebtManager;
import com.milkneko.apps.utility.water.manager.SeasonalConnectionPaymentManager;
import com.milkneko.apps.utility.water.manager.ServiceShutOffManager;
import com.milkneko.apps.utility.water.model.*;
import com.milkneko.apps.utility.water.util.SeasonsUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private SeasonEntryRepository seasonEntryRepository;
    @Autowired
    private MeasureStampRepository measureStampRepository;
    @Autowired
    private ConnectionTypeRepository connectionTypeRepository;
    @Autowired
    private SeasonalConnectionDebtManager seasonalConnectionDebtManager;
    @Autowired
    private ConfigRepository configRepository;
    @Autowired
    private SeasonalConnectionPaymentManager seasonalConnectionPaymentManager;
    @Autowired
    private ServiceShutOffManager serviceShutOffManager;

    @Transactional
    public void initialize()throws Exception{
        List<Customer> customers = customerRepository.findAll();
        if(customers.isEmpty())
        {
            System.out.println("DB empty fill with default data");
            initializeExcelData();
        }

        initializeConfigData();
        //initializeTestRegisters();
        initializeSeasonEntries();
    }

    @Transactional
    public void generateSeasonalMeasurements(int seasonIdx){
        List<Connection> connections = connectionRepository.findAll();
        SeasonEntry seasonEntry = seasonEntryRepository.findOne(SeasonsUtil.createSeasonEntryKey(seasonIdx));

        for (Connection connection : connections){
            Register register = connection.getRegister();
            MeasureStamp lastMeasureStamp = register.getLastMeasureStamp();

            // day is from 8 to 14
            // consume is from 0 to 29
            MeasureStamp measureStamp = new MeasureStamp(Date.valueOf(LocalDate.of(seasonEntry.getYear(), seasonEntry.getMonth(),
                    (int)(Math.random()*7 + 8))), lastMeasureStamp.getValue() + (int)(Math.random()*30 + 0));
            measureStamp.setSeasonEntry(seasonEntry);
            measureStamp.setPrevMeasureStamp(lastMeasureStamp);
            measureStamp.setConnection(connection);
            measureStamp.setRegister(register);
            register.setLastMeasureStamp(measureStamp);
            measureStampRepository.save(measureStamp);
        }
    }
    
    @Transactional
    public void generateSeasonalConnectionDebts(int seasonIdx){
        SeasonEntryKey seasonEntryKey = SeasonsUtil.createSeasonEntryKey(seasonIdx);
        // day if from 16 to 18
        LocalDate localDate = LocalDate.of(seasonEntryKey.getYear(), seasonEntryKey.getMonth(), (int)(16 + 3*Math.random()));
        seasonalConnectionDebtManager.generateSeasonalConnectionDebtsBySeason(seasonIdx, Date.valueOf(localDate));
    }

    @Transactional
    public void generateSeasonalConnectionPayments(int seasonIdx){
        SeasonEntryKey seasonEntryKey = SeasonsUtil.createSeasonEntryKey(seasonIdx);
        SeasonEntry seasonEntry = seasonEntryRepository.findOne(seasonEntryKey);
        // 95% probability that the debt is paid out
        // day of payment is between 20 and + [0, 20)
        for (SeasonalConnectionDebt seasonalConnectionDebt: seasonEntry.getSeasonalConnectionDebts()) {
            if(Math.random() < 0.95){
                SeasonalConnectionDebt prevSeasonalConnectionDebt = seasonalConnectionDebt.getPrevSeasonalConnectionDebt();
                Date paymentDate = Date.valueOf(LocalDate.of(seasonEntryKey.getYear(), seasonEntryKey.getMonth(), 1).plusDays((int) (20 + 20 * Math.random())));

                // we need to enforce to pay previous debts
                if(prevSeasonalConnectionDebt != null && prevSeasonalConnectionDebt.getSeasonalConnectionPayment() == null &&
                        prevSeasonalConnectionDebt.getIssuedDay().compareTo(paymentDate) < 0
                        ){
                    continue;
                }

                seasonalConnectionPaymentManager.create(
                        seasonalConnectionDebt,
                        paymentDate);
            }
        }
    }

    @Transactional
    public void generateSeasonalConnectionShutoff(){
        serviceShutOffManager.generateServiceShutOffs();
    }

    @Transactional
    public void testData(){
	    List<Customer> customers = customerRepository.findAllAndFetchConnectionsEagerly();
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

	private void initializeConfigData(){
        Config config = new Config(Config.INTEREST_RATE_PENALTY, "0.10");
        configRepository.save(config);

        config = new Config(Config.MONTHS_TO_DUE_DEBT, "1");
        configRepository.save(config);

        config = new Config(Config.MONTHS_TO_CUT_SERVICE, "2");
        configRepository.save(config);
    }

	private void initializeSeasonEntries(){
        SeasonEntry[] seasonEntries = new SeasonEntry[100];

        for(int i = 0; i < 100; i++){
            SeasonEntry seasonEntry = new SeasonEntry(i/12 + 2016, i%12 + 1);
            seasonEntries[i] = seasonEntry;
            seasonEntryRepository.save(seasonEntry);
        }
    }

    private void initializeTestRegisters(){
        for(int i = 0; i < 100; i++)
        {
            MeasureStamp measureStamp = new MeasureStamp(Date.valueOf(LocalDate.of(2016, 1, 1)), 0f);
            measureStampRepository.save(measureStamp);

            String registerName = Integer.toString((int)(Math.random()*100000 + 7000000));
            Register register = new Register(registerName, 0f);
            register.setLastMeasureStamp(measureStamp);
            measureStamp.setRegister(register);

            registerRepository.save(register);
            measureStampRepository.save(measureStamp);
        }
    }

    private void initializeExcelData() throws Exception{
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        Workbook actaEntregaWorkbook = WorkbookFactory.create(classloader.getResourceAsStream("static/ACTA_ENTREGA.xlsx"));
        int numberOfSheets = actaEntregaWorkbook.getNumberOfSheets();

        ConnectionType connectionType1 = new ConnectionType(1, "SOCIAL", "", "0.5960", "0.2440", 2.78f, 4.16f, 24);
        ConnectionType connectionType2 = new ConnectionType(2, "DOMESTICO", "11;31", "0.5960;1.0370;2.3840", "0.2440;0.4260;0.9800", 2.78f, 4.16f, 24);
        ConnectionType connectionType3 = new ConnectionType(3, "ESTATAL", "101", "2.3840;3.3020", "0.9800;1.3560", 2.78f, 4.16f, 24);
        ConnectionType connectionType4 = new ConnectionType(4, "COMERCIAL", "16", "3.9100;4.2730", "1.6060;1.7550", 2.78f, 4.16f, 24);

        connectionTypeRepository.save(connectionType1);
        connectionTypeRepository.save(connectionType2);
        connectionTypeRepository.save(connectionType3);
        connectionTypeRepository.save(connectionType4);

        ConnectionType[] connectionTypes = new ConnectionType[]{connectionType1, connectionType2, connectionType3, connectionType4};

        int count = 0;

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
                    row.getCell(0).setCellType(CellType.STRING);
                    if(row.getCell(0).getStringCellValue().trim().equals("1"))
                        break;
                }
            }

            for(; j < rows; j++)
            {
                row = sheet.getRow(j);
                row.getCell(1).setCellType(CellType.STRING);
                String codigo = row.getCell(1).getStringCellValue().trim();
                if(codigo == null || codigo.trim().equals(""))
                {
                    break;
                }

                MeasureStamp measureStamp = new MeasureStamp(Date.valueOf(LocalDate.of(2016, 1, 1)), 0f);
                measureStampRepository.save(measureStamp);

                Register register = new Register(codigo, 0f);
                register.setLastMeasureStamp(measureStamp);
                registerRepository.save(register);

                row.getCell(2).setCellType(CellType.STRING);
                row.getCell(3).setCellType(CellType.STRING);
                row.getCell(4).setCellType(CellType.STRING);
                row.getCell(5).setCellType(CellType.STRING);

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

                Connection connection = new Connection(connectionAddress, new Date(this.dateFormat.parse("2016-01-01").getTime()),
                        null, true, connectionComment);
                connection.setRegister(register);
                connection.setCustomer(customer);
                connection.setZone(zone);
                connection.setConnectionType(connectionTypes[1]);

                connectionRepository.save(connection);

                measureStamp.setConnection(connection);
                measureStamp.setRegister(register);
                measureStampRepository.save(measureStamp);

//                count++;
//                if(count > 50){
//                    break;
//                }
            }

            System.out.println(zoneName);
        }

        actaEntregaWorkbook.close();
        System.out.println(numberOfSheets);
    }
}
