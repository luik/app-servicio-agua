package com.milkneko.apps.utility.water.manager;

import com.milkneko.apps.utility.water.model.SeasonalConnectionDebt;
import com.milkneko.apps.utility.water.model.SeasonalConnectionPayment;
import com.milkneko.apps.utility.water.model.SeasonalConnectionPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class SeasonalConnectionPaymentManager {

    @Autowired
    private SeasonalConnectionPaymentRepository seasonalConnectionPaymentRepository;

    public void create(SeasonalConnectionDebt seasonalConnectionDebt, Date date) {
        SeasonalConnectionPayment seasonalConnectionPayment = new SeasonalConnectionPayment(date);
        seasonalConnectionPayment.setSeasonalConnectionDebt(seasonalConnectionDebt);

        seasonalConnectionDebt.getConnection().setLastSeasonalConnectionPayment(seasonalConnectionPayment);
        seasonalConnectionPaymentRepository.save(seasonalConnectionPayment);
    }
}
