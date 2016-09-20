package com.milkneko.apps.utility.water.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.milkneko.apps.utility.water.model.Connection;
import com.milkneko.apps.utility.water.model.ConnectionRepository;
import com.milkneko.apps.utility.water.model.MeasureStamp;
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
    @Autowired
    private ConnectionRepository connectionRepository;

    @RequestMapping(value = "ws/connection/get-measure-stamps", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MeasureStampResponse>> getMeasureStampsByConnection(@RequestBody ConnectionResponse connectionResponse){

        List<MeasureStampResponse> measureStampResponses = measureStampRepository.findAllByConnectionId(connectionResponse.getId()).stream().map(
                measureStamp -> {

                    LocalDate prevLocalDate = measureStamp.getDate().toLocalDate().minusMonths(1);
                    int prevYear = prevLocalDate.getYear();
                    int prevMonth = prevLocalDate.getMonthValue() - 1;
                    int prevLastDay = new GregorianCalendar(prevYear, prevMonth, 1).getActualMaximum(Calendar.DAY_OF_MONTH);

                    MeasureStamp lastMeasureStamp = measureStampRepository.findOneByConnectionIdAndDateBetweenOrderByDate(measureStamp.getConnection().getId(),
                            new Date(new GregorianCalendar(prevYear, prevMonth, 1).getTime().getTime()),
                            new Date(new GregorianCalendar(prevYear, prevMonth, prevLastDay).getTime().getTime()));
                    
                    float lastMeasureValue = 0;
                    if(lastMeasureStamp != null){
                    	lastMeasureValue = lastMeasureStamp.getValue(); 
                    }

                    return new MeasureStampResponse(measureStamp.getId(), measureStamp.getDate(), measureStamp.getValue(),
                            measureStamp.getConnection().getId(), measureStamp.getRegister().getRegisterId(),
                            measureStamp.getPrevSeasonalConnectionDebt() == null ? 0 : measureStamp.getPrevSeasonalConnectionDebt().getId(),
                            measureStamp.getCurrentSeasonalConnectionDebt() == null ? 0 : measureStamp.getCurrentSeasonalConnectionDebt().getId(),
                            measureStamp.getConnection().getCustomer().getName(), measureStamp.getConnection().getZone().getName(),
                            measureStamp.getConnection().getAddress(), measureStamp.getValue(), false,
                            lastMeasureValue
                    );
                }
        ).collect(Collectors.toList());

        return new ResponseEntity<List<MeasureStampResponse>>(measureStampResponses, HttpStatus.OK);
    }
    

    @RequestMapping(value = "ws/season/get-measure-stamps", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MeasureStampResponse>> getMeasureStampsBySeason(@RequestBody SeasonEntryResponse seasonEntryResponse){
        int index = seasonEntryResponse.getId();
        int year = index/12 + 2016;
        int month = index%12 - 1;
        int lastDay = new GregorianCalendar(year, month, 1).getActualMaximum(Calendar.DAY_OF_MONTH);

        int prevYear = (index - 1)/12 + 2016;
        int prevMonth = (index - 1)%12 - 1;
        int prevLastDay = new GregorianCalendar(prevYear, prevMonth, 1).getActualMaximum(Calendar.DAY_OF_MONTH);

        List<MeasureStampResponse> measureStampResponses = measureStampRepository.findByDateBetween(new Date(new GregorianCalendar(year, month, 1).getTime().getTime()),
                new Date(new GregorianCalendar(year, month, lastDay).getTime().getTime())).stream().map(
                measureStamp -> {
                    MeasureStamp lastMeasureStamp = measureStampRepository.findOneByConnectionIdAndDateBetweenOrderByDate(measureStamp.getConnection().getId(),
                            new Date(new GregorianCalendar(prevYear, prevMonth, 1).getTime().getTime()),
                            new Date(new GregorianCalendar(prevYear, prevMonth, prevLastDay).getTime().getTime()));
                    
                    float lastMeasureValue = 0;
                    if(lastMeasureStamp != null){
                    	lastMeasureValue = lastMeasureStamp.getValue(); 
                    }

                    return new MeasureStampResponse(measureStamp.getId(), measureStamp.getDate(), measureStamp.getValue(),
                        measureStamp.getConnection().getId(), measureStamp.getRegister().getRegisterId(),
                        measureStamp.getPrevSeasonalConnectionDebt() == null ? 0 : measureStamp.getPrevSeasonalConnectionDebt().getId(),
                        measureStamp.getCurrentSeasonalConnectionDebt() == null ? 0 : measureStamp.getCurrentSeasonalConnectionDebt().getId(),
                        measureStamp.getConnection().getCustomer().getName(), measureStamp.getConnection().getZone().getName(),
                        measureStamp.getConnection().getAddress(), measureStamp.getValue(), false,
                        lastMeasureValue
                        );
                }
        ).collect(Collectors.toList());

        List<Connection> connections = connectionRepository.findAll();
        Map<Integer, Connection> connectionsMap = new HashMap<>();
        for (Connection connection : connections){
            connectionsMap.put(connection.getId(), connection);
        }
        for(MeasureStampResponse measureStampResponse : measureStampResponses){
            connectionsMap.remove(measureStampResponse.getConnectionID());
        }
        for (Connection connection: connectionsMap.values()) {
            MeasureStamp lastMeasureStamp = measureStampRepository.findOneByConnectionIdAndDateBetweenOrderByDate(connection.getId(),
                    new Date(new GregorianCalendar(prevYear, prevMonth, 1).getTime().getTime()),
                    new Date(new GregorianCalendar(prevYear, prevMonth, prevLastDay).getTime().getTime()));
            
            float lastMeasureValue = 0;
            if(lastMeasureStamp != null){
            	lastMeasureValue = lastMeasureStamp.getValue(); 
            }

            measureStampResponses.add(
                    new MeasureStampResponse(0, new Date(new GregorianCalendar(year, month, 1).getTime().getTime()), 0,
                            connection.getId(), connection.getRegister().getRegisterId(),
                            0,
                            0,
                            connection.getCustomer().getName(), connection.getZone().getName(),
                            connection.getAddress(), 0, true, lastMeasureValue
            ));
        }
        return new ResponseEntity<List<MeasureStampResponse>>(measureStampResponses, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/save-measure-stamps", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> saveMeasureStamps(@RequestBody List<MeasureStampResponse> measureStampResponses){
        for (MeasureStampResponse measureStampResponse: measureStampResponses) {
            if(measureStampResponse.isPending()){
                MeasureStamp measureStamp = new MeasureStamp(measureStampResponse.getDate(), measureStampResponse.getModifiedValue());
                Connection connection = connectionRepository.findOne(measureStampResponse.getConnectionID());

                measureStamp.setConnection(connection);
                measureStamp.setRegister(connection.getRegister());
                measureStampRepository.save(measureStamp);
            }
            else{
                MeasureStamp measureStamp = measureStampRepository.findOne(measureStampResponse.getId());
                measureStamp.setValue(measureStampResponse.getModifiedValue());;
                measureStampRepository.save(measureStamp);
            }
        }

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
