package com.milknekp.apps.utility.water.controller;

import com.milknekp.apps.utility.water.model.RegisterRepository;
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

}
