package com.milknekp.apps.utility.water.controller;


import com.milknekp.apps.utility.water.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @RequestMapping(value = "ws/add-customer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Boolean> addCustomer(@RequestBody Customer customer){

        System.out.println(customer.getFirstName() + "," + customer.getLastName());

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
