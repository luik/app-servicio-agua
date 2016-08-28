package com.milkneko.apps.utility.water.controller;

import com.milkneko.apps.utility.water.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ConnectionController{

    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private RegisterRepository registerRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ZoneRepository zoneRepository;

    @RequestMapping(value = "ws/add-connection", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addConnection(@RequestBody ConnectionResponse _connectionResponse){
        Connection connection = new Connection();
        Register register = registerRepository.getOne(_connectionResponse.getRegisterID());
        Customer customer = customerRepository.getOne(_connectionResponse.getCustomerID());
        Zone zone = zoneRepository.getOne(_connectionResponse.getZoneID());
        String address = _connectionResponse.getAddress();
        boolean isActive = _connectionResponse.isActive();
        Date startDate = _connectionResponse.getStartDate();
        String comment = _connectionResponse.getComment();

        if(register == null || customer == null || zone == null){
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }

        connection.setRegister(register);
        connection.setCustomer(customer);
        connection.setZone(zone);
        connection.setAddress(address);
        connection.setActive(isActive);
        connection.setStartDate(startDate);
        
        if(!isActive){
        	connection.setActive(isActive);
            connection.setEndDate(new Date(new java.util.Date().getTime()));
        }
        
        connection.setComment(comment);
        connectionRepository.save(connection);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/get-connections", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConnectionResponse>> getConnections(){
    	System.out.println("get connecitons: " + new java.util.Date().getTime());
    	
        List<ConnectionResponse> connections = connectionRepository.findAll().stream().map(
                connection -> new ConnectionResponse(connection.getId(), connection.getCustomer().getId(), connection.getCustomer().getName(),
                connection.getZone().getId(), connection.getZone().getName(),
                        connection.getRegister().getId(), connection.getRegister().getRegisterId(), connection.getAddress(),
                        connection.getStartDate(), connection.getEndDate(), connection.isActive(), connection.getComment())
        ).collect(Collectors.toList());
        
        System.out.println(new java.util.Date().getTime());

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

    @RequestMapping(value = "ws/update-connection", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateConnection(@RequestBody ConnectionResponse _connectionResponse)
    {
        Connection connection = connectionRepository.getOne(_connectionResponse.getId());
        Register register = registerRepository.getOne(_connectionResponse.getRegisterID());

        if (connection.isActive() && !_connectionResponse.isActive()) {
            connection.setActive(_connectionResponse.isActive());
            connection.setEndDate(new Date(new java.util.Date().getTime()));
        }
        if (register == null) {
            return new ResponseEntity<Boolean>(false, HttpStatus.OK);
        }

        if(register.getId() != connection.getRegister().getId()) {
            connection.setRegister(register);
        }
        connection.setComment(_connectionResponse.getComment());

        connectionRepository.save(connection);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    /*
    @RequestMapping(value = "ws/delete-connection", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteConnection(@RequestBody ConnectionResponse connection){
        connectionRepository.delete(connection.getId());
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    */

}
