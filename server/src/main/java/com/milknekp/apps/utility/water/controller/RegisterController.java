package com.milknekp.apps.utility.water.controller;

import com.milknekp.apps.utility.water.model.Register;
import com.milknekp.apps.utility.water.model.RegisterRepository;
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
public class RegisterController {

    @Autowired
    private RegisterRepository registerRepository;

    @RequestMapping(value = "ws/get-registers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RegisterResponse>> getRegisters(){
        List<RegisterResponse> registers = registerRepository.findAll().stream().map(
                register -> new RegisterResponse(register.getId(), register.getRegisterId(), register.getValue())
        ).collect(Collectors.toList());

        return new ResponseEntity<List<RegisterResponse>>(registers, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/get-available-registers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RegisterResponse>> getAvailableRegisters(){
        List<RegisterResponse> registers = registerRepository.findAll().stream().
                filter(register -> register.getConnection() == null).
                map(register -> new RegisterResponse(register.getId(), register.getRegisterId(), register.getValue())).
                collect(Collectors.toList());

        return new ResponseEntity<List<RegisterResponse>>(registers, HttpStatus.OK);
    }

    /*
    @RequestMapping(value = "ws/get-customer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> getCustomer(@RequestBody CustomerResponse customer){
        Customer customerDO = customerRepository.findOne(customer.getId());
        CustomerResponse customerResponse = new CustomerResponse(customerDO.getId(), customerDO.getName(), customerDO.getDocumentId());
        return new ResponseEntity<CustomerResponse>(customerResponse, HttpStatus.OK);
    }
    * */

    public ResponseEntity<RegisterResponse> getRegister(){
        return null;
    }

}
