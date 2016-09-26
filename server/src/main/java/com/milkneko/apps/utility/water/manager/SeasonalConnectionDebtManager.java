package com.milkneko.apps.utility.water.manager;

import com.milkneko.apps.utility.water.model.*;
import com.milkneko.apps.utility.water.util.SeasonsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Component
public class SeasonalConnectionDebtManager{

    @Autowired
    private SeasonEntryRepository seasonEntryRepository;
    @Autowired
    private MeasureStampRepository measureStampRepository;
    @Autowired
    private SeasonalConnectionDebtRepository seasonalConnectionDebtRepository;
    @Autowired
    private ConfigRepository configRepository;

    public void generateSeasonalConnectionDebtsBySeason(int seasonIndex){
        generateSeasonalConnectionDebtsBySeason(seasonIndex, new Date(new java.util.Date().getTime()));
    }

    public void generateSeasonalConnectionDebtsBySeason(int seasonIndex, Date issueDate){
        SeasonEntry seasonEntry = seasonEntryRepository.getOne(SeasonsUtil.createSeasonEntryKey(seasonIndex));
        Collection<MeasureStamp> measureStamps = seasonEntry.getMeasureStamp();
        //we need to filter the measures the have the same connection;
        //it need to be not necessary, we will not filter
        for (MeasureStamp measureStamp : measureStamps) {
            // if the measurement stamp has a seasonal connection debt do nothing
            if(measureStamp.getSeasonalConnectionDebt() != null){
                continue;
            }

            MeasureStamp prevMeasureStamp = measureStamp.getPrevMeasureStamp();

            // The register must be the same, in order to operate with the measurement values
            if(prevMeasureStamp.getRegister().getId() != measureStamp.getRegister().getId()){
                continue;
            }

            LocalDate dueDate = issueDate.toLocalDate().plusMonths(configRepository.findOneByName(Config.MONTHS_TO_DUE_DEBT).getIntValue());
            SeasonalConnectionDebt seasonalConnectionDebt = new SeasonalConnectionDebt(issueDate, Date.valueOf(dueDate));

            seasonalConnectionDebt.setConnection(measureStamp.getConnection());
            seasonalConnectionDebt.setSeasonEntry(seasonEntry);
            seasonalConnectionDebt.setMeasureStamp(measureStamp);

            if(measureStamp.getConnection().getLastSeasonalConnectionDebt() != null) {
                seasonalConnectionDebt.setPrevSeasonalConnectionDebt(measureStamp.getConnection().getLastSeasonalConnectionDebt());

                seasonalConnectionDebt.getPrevSeasonalConnectionDebt().setNextSeasonalConnectionDebt(seasonalConnectionDebt);
            }
            measureStamp.getConnection().setLastSeasonalConnectionDebt(seasonalConnectionDebt);

            seasonalConnectionDebtRepository.save(seasonalConnectionDebt);
        }
    }


}
