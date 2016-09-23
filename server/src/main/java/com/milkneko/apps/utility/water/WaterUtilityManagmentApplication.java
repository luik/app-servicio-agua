package com.milkneko.apps.utility.water;

import com.milkneko.apps.utility.water.manager.SeasonalConnectionDebtExcelPrinter;
import com.milkneko.apps.utility.water.manager.SeasonalConnectionDebtManager;
import com.milkneko.apps.utility.water.manager.SeasonalConnectionDebtPDFPrinter;
import com.milkneko.apps.utility.water.manager.SeasonalConnectionPaymentPDFPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WaterUtilityManagmentApplication implements CommandLineRunner{

    @Autowired
    private DataInitializer dataInitializer;
    @Autowired
    SeasonalConnectionDebtManager seasonalConnectionDebtManager;
    @Autowired
    SeasonalConnectionDebtPDFPrinter seasonalConnectionDebtPDFPrinter;
    @Autowired
    SeasonalConnectionPaymentPDFPrinter seasonalConnectionPaymentPDFPrinter;
    @Autowired
    SeasonalConnectionDebtExcelPrinter seasonalConnectionDebtExcelPrinter;

	public static void main(String[] args) {
		SpringApplication.run(WaterUtilityManagmentApplication.class, args);
	}

    @Override
    public void run(String... strings) throws Exception {
        dataInitializer.initialize();
    }
}
