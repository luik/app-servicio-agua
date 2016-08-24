package com.milknekp.apps.utility.water.controller;

import com.milknekp.apps.utility.water.model.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ZoneController {

    @Autowired
    private ZoneRepository zoneRepository;

    @RequestMapping(value = "ws/get-zones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ZoneResponse>> getZones(){
        List<ZoneResponse> zones = zoneRepository.findAll().stream().map(
                zone -> new ZoneResponse(zone.getId(), zone.getName())
        ).collect(Collectors.toList());

        return new ResponseEntity<List<ZoneResponse>>(zones, HttpStatus.OK);
    }

}
