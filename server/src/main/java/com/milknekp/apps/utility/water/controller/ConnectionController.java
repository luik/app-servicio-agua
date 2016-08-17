package com.milknekp.apps.utility.water.controller;

import com.milknekp.apps.utility.water.model.Connection;
import com.milknekp.apps.utility.water.model.ConnectionRepository;
import com.milknekp.apps.utility.water.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ConnectionController{

    @Autowired
    private ConnectionRepository connectionRepository;

    @RequestMapping(value = "ws/get-connections", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConnectionResponse>> getCustomers(){
        List<ConnectionResponse> connections = connectionRepository.findAll().stream().map(
                connection -> new ConnectionResponse(connection.getId(), connection.getCustomer().getName(),
                connection.getZone().getName(), connection.getRegister().getRegisterId(), connection.getAddress())
        ).collect(Collectors.toList());

        return new ResponseEntity<List<ConnectionResponse>>(connections, HttpStatus.OK);
    }

}
