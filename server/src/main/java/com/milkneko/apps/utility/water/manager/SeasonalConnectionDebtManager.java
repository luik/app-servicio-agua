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
        List<MeasureStamp> measureStamps =
                measureStampRepository.findByDateBetween(SeasonsUtil.getFirstDayOfSeasonIdx(seasonIndex),
                        SeasonsUtil.getLastDayOfSeasonIdx(seasonIndex));

        //we need to filter the measures the have the same connection
        Map<Integer, List<MeasureStamp>> connectionId2MeasurementsStamps = new HashMap<>();
        for (MeasureStamp measureStamp : measureStamps) {
            if(!connectionId2MeasurementsStamps.containsKey(measureStamp.getConnection().getId())){
                connectionId2MeasurementsStamps.put(measureStamp.getConnection().getId(), new ArrayList<>());
            }
            connectionId2MeasurementsStamps.get(measureStamp.getConnection().getId()).add(measureStamp);
        }
        List<MeasureStamp> filteredMeasureStamps = new ArrayList<>();
        for(int connectionId: connectionId2MeasurementsStamps.keySet()){
            Collections.sort(connectionId2MeasurementsStamps.get(connectionId), (o1, o2) -> -o1.getDate().compareTo(o2.getDate()));
            filteredMeasureStamps.add(connectionId2MeasurementsStamps.get(connectionId).get(0));
        }

        for (MeasureStamp measureStamp : filteredMeasureStamps) {
            // if the measurement stamp has a seasonal connection debt do nothing
            if(measureStamp.getCurrentSeasonalConnectionDebt() != null){
                continue;
            }

            SeasonEntry seasonEntry = seasonEntryRepository.findOne(SeasonsUtil.createSeasonEntryKey(seasonIndex));

            //get the previous measurement
            MeasureStamp prevMeasureStamp = measureStampRepository.findFirstByConnectionIdAndDateLessThanOrderByDateDesc(
                    measureStamp.getConnection().getId(), measureStamp.getDate());

            // we only have a only one measurement, we need at least one more
            if(prevMeasureStamp == null){
                continue;
            }

            // The register must be the same, in order to operate with the measurements value
            // It need at least one extra measurement
            if(prevMeasureStamp.getRegister().getId() != measureStamp.getRegister().getId()){
                continue;
            }

            LocalDate dueDate = issueDate.toLocalDate().plusMonths(configRepository.findOneByName(Config.MONTHS_TO_DUE_DEBT).getIntValue());
            SeasonalConnectionDebt seasonalConnectionDebt = new SeasonalConnectionDebt(issueDate, Date.valueOf(dueDate));

            seasonalConnectionDebt.setConnection(measureStamp.getConnection());
            seasonalConnectionDebt.setSeasonEntry(seasonEntry);
            seasonalConnectionDebt.setInitialMeasureStamp(prevMeasureStamp);
            seasonalConnectionDebt.setFinalMeasureStamp(measureStamp);

            seasonalConnectionDebtRepository.save(seasonalConnectionDebt);
        }
    }
}
