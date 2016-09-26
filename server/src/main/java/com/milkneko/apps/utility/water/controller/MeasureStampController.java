package com.milkneko.apps.utility.water.controller;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.milkneko.apps.utility.water.model.Connection;
import com.milkneko.apps.utility.water.model.ConnectionRepository;
import com.milkneko.apps.utility.water.model.MeasureStamp;
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
                	MeasureStamp lastMeasureStamp = measureStampRepository.findFirstByConnectionIdAndDateLessThanOrderByDateDesc(measureStamp.getConnection().getId(), measureStamp.getDate());
                    
                    return MeasureStampResponse.createFrom(measureStamp, lastMeasureStamp);
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

        List<MeasureStampResponse> measureStampResponses = measureStampRepository.findByDateBetween(new Date(new GregorianCalendar(year, month, 1).getTime().getTime()),
                new Date(new GregorianCalendar(year, month, lastDay).getTime().getTime())).stream().map(
                measureStamp -> {
                    MeasureStamp lastMeasureStamp = measureStampRepository
                            .findFirstByConnectionIdAndDateLessThanOrderByDateDesc(measureStamp.getConnection().getId(), measureStamp.getDate());
                    
                    return MeasureStampResponse.createFrom(measureStamp, lastMeasureStamp);
                }
        ).collect(Collectors.toList());

        List<Connection> connections = connectionRepository.findAll().stream().filter(connection -> connection.isActive()).collect(Collectors.toList());
        Map<Integer, Connection> connectionsMap = new HashMap<>();
        for (Connection connection : connections){
            connectionsMap.put(connection.getId(), connection);
        }
        for(MeasureStampResponse measureStampResponse : measureStampResponses){
            connectionsMap.remove(measureStampResponse.getConnectionID());
        }
        for (Connection connection: connectionsMap.values()) {
            MeasureStamp lastMeasureStamp = measureStampRepository.findTwoByConnectionIdOrderByDate(connection.getId()).get(0);

            float lastMeasureValue = connection.getRegister().getInitialValue();
            if(lastMeasureStamp != null){
            	lastMeasureValue = lastMeasureStamp.getValue(); 
            }

            measureStampResponses.add(
                    new MeasureStampResponse(0, new Date(new GregorianCalendar(year, month, 1).getTime().getTime()), 0,
                            connection.getId(), connection.getRegister().getRegisterId(),
                            connection.getCustomer().getName(), connection.getZone().getName(),
                            connection.getAddress(), 0, true, lastMeasureValue
            ));
        }
        for (Connection connection : connections){
            if(connection.getMeasureStamps().size() < 2){
                System.out.println("add");

                measureStampResponses.add(
                        new MeasureStampResponse(0, new Date(new GregorianCalendar(year, month, 1).getTime().getTime()), 0,
                                connection.getId(), connection.getRegister().getRegisterId(),
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
