package com.milknekp.apps.utility.water.controller;


import com.milknekp.apps.utility.water.model.Customer;
import com.milknekp.apps.utility.water.model.CustomerRepository;
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

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value = "ws/add-customer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addCustomer(@RequestBody CustomerResponse customerResponse){
        Customer customer = new Customer(customerResponse.getName(), customerResponse.getDocumentId());

        customerRepository.save(customer);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/get-customers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerResponse>> getCustomers(){
        List<CustomerResponse> customers = new ArrayList<>();

        for (Customer customer :
                customerRepository.findAll()) {
            customers.add(new CustomerResponse(customer.getName(), customer.getDocumentId()));
        }

        return new ResponseEntity<List<CustomerResponse>>(customers, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/get-customer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getCustomer(@RequestBody Customer customer){
        return new ResponseEntity<Customer>(customerRepository.findOne(customer.getId()), HttpStatus.OK);
    }

    @RequestMapping(value = "ws/delete-customer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteCustomer(@RequestBody Customer customer){
        customerRepository.delete(customer.getId());
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

}
