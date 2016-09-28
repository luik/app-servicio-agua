package com.milkneko.apps.utility.water;

import com.milkneko.apps.utility.water.manager.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WaterUtilityManagmentApplication implements CommandLineRunner{

    @Autowired
    private DataInitializer dataInitializer;
    @Autowired
    private SeasonalConnectionDebtManager seasonalConnectionDebtManager;
    @Autowired
    private SeasonalConnectionDebtPDFPrinter seasonalConnectionDebtPDFPrinter;
    @Autowired
    private SeasonalConnectionPaymentPDFPrinter seasonalConnectionPaymentPDFPrinter;
    @Autowired
    private SeasonalConnectionDebtExcelPrinter seasonalConnectionDebtExcelPrinter;
    @Autowired
    private ServiceShutOffManager serviceShutOffManager;
    @Autowired
    private SeasonalConnectionPaymentManager seasonalConnectionPaymentManager;
    @Autowired
    private MeasureStampExcelPrinter measureStampExcelPrinter;
    @Autowired
    private MeasureStampManager measureStampManager;

	public static void main(String[] args) {
		SpringApplication.run(WaterUtilityManagmentApplication.class, args);
	}

    @Override
    public void run(String... strings) throws Exception {
        dataInitializer.initialize();
        dataInitializer.testData();

        for(int seasonEntryIdx = 1; seasonEntryIdx <= 8; seasonEntryIdx++){
            System.out.println("Initializing data for season " + seasonEntryIdx);

            dataInitializer.generateSeasonalMeasurements(seasonEntryIdx);
            dataInitializer.generateSeasonalConnectionDebts(seasonEntryIdx);
            dataInitializer.generateSeasonalConnectionPayments(seasonEntryIdx);
        }

        System.out.println("Generating service shutoff");
        ///dataInitializer.generateSeasonalConnectionShutoff();

    }
}
