package com.milkneko.apps.utility.water.manager;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.milkneko.apps.utility.water.model.MeasureStamp;
import com.milkneko.apps.utility.water.model.MeasureStampRepository;
import com.milkneko.apps.utility.water.model.SeasonEntry;
import com.milkneko.apps.utility.water.model.SeasonEntryKey;
import com.milkneko.apps.utility.water.model.SeasonEntryRepository;
import com.milkneko.apps.utility.water.model.SeasonalConnectionDebt;
import com.milkneko.apps.utility.water.model.SeasonalConnectionDebtRepository;

@Component
public class SeasonalConnectionDebtManager{

    @Autowired
    private SeasonEntryRepository seasonEntryRepository;
    @Autowired
    private MeasureStampRepository measureStampRepository;
    @Autowired
    private SeasonalConnectionDebtRepository seasonalConnectionDebtRepository;


    public void generateSeasonalConnectionDebtsBySeason(int seasonIndex){
        int year = seasonIndex/12 + 2016;
        int month = seasonIndex%12 - 1;
        int lastDay = new GregorianCalendar(year, month, 1).getActualMaximum(Calendar.DAY_OF_MONTH);

        int prevYear = (seasonIndex - 1)/12 + 2016;
        int prevMonth = (seasonIndex - 1)%12 - 1;
        int prevLastDay = new GregorianCalendar(prevYear, prevMonth, 1).getActualMaximum(Calendar.DAY_OF_MONTH);

        Date startDate = new Date(new GregorianCalendar(year, month, 1).getTime().getTime());
        Date endDate = new Date(new GregorianCalendar(year, month, lastDay).getTime().getTime());

        Date prevStartDate = new Date(new GregorianCalendar(prevYear, prevMonth, 1).getTime().getTime());
        Date prevEndDate = new Date(new GregorianCalendar(prevYear, prevMonth, prevLastDay).getTime().getTime());

        List<MeasureStamp> measureStamps = measureStampRepository.findByDateBetween(startDate, endDate);
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

            SeasonEntry seasonEntry = seasonEntryRepository.findOne(new SeasonEntryKey(year, month + 1));

            ///MeasureStamp prevMeasureStamp = measureStampRepository.findOneByConnectionIdAndDateBetweenOrderByDate(measureStamp.getConnection().getId(), prevStartDate, prevEndDate);
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

            SeasonalConnectionDebt seasonalConnectionDebt = new SeasonalConnectionDebt(new Date(new java.util.Date().getTime()));
            seasonalConnectionDebt.setConnection(measureStamp.getConnection());
            seasonalConnectionDebt.setSeasonEntry(seasonEntry);
            seasonalConnectionDebt.setInitialMeasureStamp(prevMeasureStamp);
            seasonalConnectionDebt.setFinalMeasureStamp(measureStamp);

            seasonalConnectionDebtRepository.save(seasonalConnectionDebt);
        }
    }




}
