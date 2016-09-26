package com.milkneko.apps.utility.water.controller;

import com.milkneko.apps.utility.water.model.ServiceShutOffRepository;
import com.milkneko.apps.utility.water.response.ServiceShutOffResponse;
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
public class ServiceShutOffController {

    @Autowired
    private ServiceShutOffRepository serviceShutOffRepository;

    @RequestMapping(value = "ws/get-service-shut-offs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ServiceShutOffResponse>> getZones(){
        List<ServiceShutOffResponse> serviceShutOffResponses = serviceShutOffRepository.findAll().stream().map(
                serviceShutOff -> ServiceShutOffResponse.createFrom(serviceShutOff)
        ).collect(Collectors.toList());

        return new ResponseEntity<List<ServiceShutOffResponse>>(serviceShutOffResponses, HttpStatus.OK);
    }
}
