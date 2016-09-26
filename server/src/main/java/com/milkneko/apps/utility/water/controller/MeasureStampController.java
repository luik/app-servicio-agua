package com.milkneko.apps.utility.water.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.milkneko.apps.utility.water.model.*;
import com.milkneko.apps.utility.water.util.SeasonsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.milkneko.apps.utility.water.response.ConnectionResponse;
import com.milkneko.apps.utility.water.response.MeasureStampResponse;
import com.milkneko.apps.utility.water.response.SeasonEntryResponse;

@RestController
public class MeasureStampController {

    @Autowired
    private MeasureStampRepository measureStampRepository;
    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private SeasonEntryRepository seasonEntryRepository;

    @RequestMapping(value = "ws/connection/get-measure-stamps", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MeasureStampResponse>> getMeasureStampsByConnection(@RequestBody ConnectionResponse connectionResponse){

        List<MeasureStampResponse> measureStampResponses = measureStampRepository.findAllByConnectionId(connectionResponse.getId()).stream().
        filter(measureStamp -> measureStamp.getSeasonEntry() != null).map(
                measureStamp -> MeasureStampResponse.createFrom(measureStamp)).collect(Collectors.toList());

        return new ResponseEntity<List<MeasureStampResponse>>(measureStampResponses, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/season/get-measure-stamps", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MeasureStampResponse>> getMeasureStampsBySeason(@RequestBody SeasonEntryResponse seasonEntryResponse){
        SeasonEntry seasonEntry = seasonEntryRepository.findOne(SeasonsUtil.createSeasonEntryKey(seasonEntryResponse.getId()));

        List<MeasureStampResponse> measureStampResponses = seasonEntry.getMeasureStamp().stream().map(
                measureStamp -> MeasureStampResponse.createFrom(measureStamp)).collect(Collectors.toList());

        List<Connection> connections = connectionRepository.findAll().stream().filter(connection -> connection.isActive()).collect(Collectors.toList());
        Map<Integer, Connection> connectionsMap = new HashMap<>();
        for (Connection connection : connections){
            connectionsMap.put(connection.getId(), connection);
        }
        for(MeasureStampResponse measureStampResponse : measureStampResponses){
            connectionsMap.remove(measureStampResponse.getConnectionID());
        }
        for (Connection connection: connectionsMap.values()) {
            measureStampResponses.add(
                    new MeasureStampResponse(0, Date.valueOf(LocalDate.of(seasonEntry.getYear(), seasonEntry.getMonth(), 1)), 0,
                            connection.getId(), connection.getRegister().getRegisterId(), 0, seasonEntryResponse.getId(),
                            connection.getCustomer().getName(), connection.getZone().getName(),
                            connection.getAddress(), 0, true, connection.getRegister().getLastMeasureStamp().getValue()
            ));
        }
        for (Connection connection : connections){
            if(connection.getMeasureStamps().size() < 2){
                System.out.println("add");

                measureStampResponses.add(
                        new MeasureStampResponse(0, Date.valueOf(LocalDate.of(seasonEntry.getYear(), seasonEntry.getMonth(), 1)), 0,
                                connection.getId(), connection.getRegister().getRegisterId(), 0, seasonEntryResponse.getId(),
                                connection.getCustomer().getName(), connection.getZone().getName(),
                                connection.getAddress(), 0, true, connection.getRegister().getInitialValue()
                        ));
            }
        }

        return new ResponseEntity<List<MeasureStampResponse>>(measureStampResponses, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/save-measure-stamps", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> saveMeasureStamps(@RequestBody List<MeasureStampResponse> measureStampResponses){

        for (MeasureStampResponse measureStampResponse: measureStampResponses) {
            SeasonEntry seasonEntry = seasonEntryRepository.findOne(SeasonsUtil.createSeasonEntryKey(measureStampResponse.getSeasonEntryID()));

            if(measureStampResponse.isPending()){
                MeasureStamp measureStamp = new MeasureStamp(measureStampResponse.getDate(), measureStampResponse.getModifiedValue());
                Connection connection = connectionRepository.findOne(measureStampResponse.getConnectionID());
                measureStamp.setPrevMeasureStamp(connection.getRegister().getLastMeasureStamp());
                measureStamp.setConnection(connection);
                measureStamp.setRegister(connection.getRegister());
                connection.getRegister().setLastMeasureStamp(measureStamp);
                measureStamp.setSeasonEntry(seasonEntry);
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
