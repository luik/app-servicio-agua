package com.milkneko.apps.utility.water.controller;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.milkneko.apps.utility.water.model.MeasureStampRepository;
import com.milkneko.apps.utility.water.response.ConnectionResponse;
import com.milkneko.apps.utility.water.response.MeasureStampResponse;
import com.milkneko.apps.utility.water.response.SeasonEntryResponse;

@RestController
public class MeasureStampController {

    @Autowired
    private MeasureStampRepository measureStampRepository;

    @RequestMapping(value = "ws/connection/get-measure-stamps", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MeasureStampResponse>> getMeasureStamps(@RequestBody ConnectionResponse connectionResponse){

        List<MeasureStampResponse> measureStampResponses = measureStampRepository.findAllByConnectionId(connectionResponse.getId()).stream().map(
                measureStamp -> new MeasureStampResponse(measureStamp.getDate(), measureStamp.getValue(),
                        measureStamp.getConnection().getId(), measureStamp.getRegister().getRegisterId(),
                        measureStamp.getPrevSeasonalConnectionDebt() == null ? 0 : measureStamp.getPrevSeasonalConnectionDebt().getId(),
                        measureStamp.getCurrentSeasonalConnectionDebt() == null ? 0 : measureStamp.getCurrentSeasonalConnectionDebt().getId()		
                        )
        ).collect(Collectors.toList());

        return new ResponseEntity<List<MeasureStampResponse>>(measureStampResponses, HttpStatus.OK);
    }
    

    @RequestMapping(value = "ws/season/get-measure-stamps", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MeasureStampResponse>> getMeasureStampsBySeason(@RequestBody SeasonEntryResponse seasonEntryResponse){
        int index = seasonEntryResponse.getId();
        int year = index/12 + 2016;
        int month = index%12 - 1;
        int lastDay = new GregorianCalendar(year, month, 1).getActualMaximum(Calendar.DAY_OF_MONTH);

        List<MeasureStampResponse> measureStampResponses = measureStampRepository.findByDateBetween(new Date(new GregorianCalendar(year, month, 1).getTime().getTime()),
                new Date(new GregorianCalendar(year, month, lastDay).getTime().getTime())).stream().map(
                measureStamp -> new MeasureStampResponse(measureStamp.getDate(), measureStamp.getValue(),
                        measureStamp.getConnection().getId(), measureStamp.getRegister().getRegisterId(),
                        measureStamp.getPrevSeasonalConnectionDebt() == null ? 0 : measureStamp.getPrevSeasonalConnectionDebt().getId(),
                        measureStamp.getCurrentSeasonalConnectionDebt() == null ? 0 : measureStamp.getCurrentSeasonalConnectionDebt().getId()
                        )
        ).collect(Collectors.toList());

        return new ResponseEntity<List<MeasureStampResponse>>(measureStampResponses, HttpStatus.OK);
    }


}
