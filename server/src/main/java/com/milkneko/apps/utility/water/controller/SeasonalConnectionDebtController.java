package com.milkneko.apps.utility.water.controller;

import com.milkneko.apps.utility.water.model.*;
import com.milkneko.apps.utility.water.response.ConnectionResponse;
import com.milkneko.apps.utility.water.response.MeasureStampResponse;
import com.milkneko.apps.utility.water.response.SeasonEntryResponse;
import com.milkneko.apps.utility.water.response.SeasonalConnectionDebtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SeasonalConnectionDebtController {

    @Autowired
    private SeasonalConnectionDebtRepository seasonalConnectionDebtRepository;
    @Autowired
    private MeasureStampRepository measureStampRepository;
    @Autowired
    private SeasonEntryRepository seasonEntryRepository;

    @RequestMapping(value = "ws/connection/get-seasonal-connection-debts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SeasonalConnectionDebtResponse>> getSeasonalConnectionDebtsByConnection(@RequestBody ConnectionResponse connectionResponse){

        List<SeasonalConnectionDebtResponse> seasonalConnectionDebts = seasonalConnectionDebtRepository.findAllByConnectionId(connectionResponse.getId()).stream().map(
                seasonalConnectionDebt -> new SeasonalConnectionDebtResponse(seasonalConnectionDebt.getConnection().getId(), seasonalConnectionDebt.getIssuedDay(),
                        seasonalConnectionDebt.getInitialMeasureStamp().getValue(), seasonalConnectionDebt.getFinalMeasureStamp().getValue(),
                        seasonalConnectionDebt.getSeasonEntry().getYear(), seasonalConnectionDebt.getSeasonEntry().getMonth()
                )
        ).collect(Collectors.toList());

        return new ResponseEntity<List<SeasonalConnectionDebtResponse>>(seasonalConnectionDebts, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/season/get-seasonal-connection-debts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SeasonalConnectionDebtResponse>> getSeasonalConnectionDebtsBySeason(@RequestBody SeasonEntryResponse seasonEntryResponse){
        int index = seasonEntryResponse.getId();
        int year = index/12 + 2016;
        int month = index%12 - 1;

        List<SeasonalConnectionDebtResponse> seasonalConnectionDebts = seasonalConnectionDebtRepository.findAllBySeasonEntryIdYearAndSeasonEntryIdMonth(year, month).stream().map(
                seasonalConnectionDebt -> new SeasonalConnectionDebtResponse(seasonalConnectionDebt.getConnection().getId(), seasonalConnectionDebt.getIssuedDay(),
                        seasonalConnectionDebt.getInitialMeasureStamp().getValue(), seasonalConnectionDebt.getFinalMeasureStamp().getValue(),
                        seasonalConnectionDebt.getSeasonEntry().getYear(), seasonalConnectionDebt.getSeasonEntry().getMonth()
                )
        ).collect(Collectors.toList());

        return new ResponseEntity<List<SeasonalConnectionDebtResponse>>(seasonalConnectionDebts, HttpStatus.OK);
    }

    @RequestMapping(value = "/ws/season/generate-seasonal-connection-debts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> generateSeasonalConnectionDebtsBySeason(@RequestBody SeasonEntryResponse seasonEntryResponse) {
    	System.out.println("generate");
    	
        int index = seasonEntryResponse.getId();
        int year = index/12 + 2016;
        int month = index%12 - 1;
        int lastDay = new GregorianCalendar(year, month, 1).getActualMaximum(Calendar.DAY_OF_MONTH);

        List<MeasureStamp> measureStamps = measureStampRepository.findByDateBetween(new Date(new GregorianCalendar(year, month, 1).getTime().getTime()),
                new Date(new GregorianCalendar(year, month, lastDay).getTime().getTime()));

        for (MeasureStamp measureStamp : measureStamps) {
            SeasonEntry seasonEntry = seasonEntryRepository.findOne(new SeasonEntryKey(year, month));

            SeasonalConnectionDebt seasonalConnectionDebt = new SeasonalConnectionDebt(new Date(new java.util.Date().getTime()));
            seasonalConnectionDebt.setConnection(measureStamp.getConnection());
            seasonalConnectionDebt.setSeasonEntry(seasonEntry);
            seasonalConnectionDebt.setInitialMeasureStamp(measureStamp);
            seasonalConnectionDebt.setFinalMeasureStamp(measureStamp);

            seasonalConnectionDebtRepository.save(seasonalConnectionDebt);
        }

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

}
