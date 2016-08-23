package com.milknekp.apps.utility.water.controller;

import com.milknekp.apps.utility.water.model.Connection;
import com.milknekp.apps.utility.water.model.ConnectionRepository;
import com.milknekp.apps.utility.water.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping(value = "ws/add-connection", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addConnection(@RequestBody ConnectionResponse connectionResponse){

        Connection connection = new Connection(connectionResponse.getAddress(), connectionResponse.getStartDate(),
                connectionResponse.getEndDate(), connectionResponse.isActive(), connectionResponse.getComment());
        connectionRepository.save(connection);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/get-connections", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConnectionResponse>> getConnections(){
        List<ConnectionResponse> connections = connectionRepository.findAll().stream().map(
                connection -> new ConnectionResponse(connection.getId(), connection.getCustomer().getId(), connection.getCustomer().getName(),
                connection.getZone().getId(), connection.getZone().getName(),
                        connection.getRegister().getId(), connection.getRegister().getRegisterId(), connection.getAddress(),
                        connection.getStartDate(), connection.getEndDate(), connection.isActive(), connection.getComment())
        ).collect(Collectors.toList());

        return new ResponseEntity<List<ConnectionResponse>>(connections, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/get-connection", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConnectionResponse> getConnection(@RequestBody ConnectionResponse _connection){
        Connection connection = connectionRepository.findOne(_connection.getId());
        ConnectionResponse connectionResponse = new ConnectionResponse(connection.getId(), connection.getCustomer().getId(), connection.getCustomer().getName(),
                connection.getZone().getId(), connection.getZone().getName(),
                connection.getRegister().getId(), connection.getRegister().getRegisterId(), connection.getAddress(),
                connection.getStartDate(), connection.getEndDate(), connection.isActive(), connection.getComment());
        return new ResponseEntity<ConnectionResponse>(connectionResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/delete-connection", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteConnection(@RequestBody ConnectionResponse connection){
        connectionRepository.delete(connection.getId());
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

}
