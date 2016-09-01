package com.milkneko.apps.utility.water.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.milkneko.apps.utility.water.model.MeasureStampRepository;
import com.milkneko.apps.utility.water.response.ConnectionResponse;
import com.milkneko.apps.utility.water.response.MeasureStampResponse;

@RestController
public class MeasureStampController {

    @Autowired
    private MeasureStampRepository measureStampRepository;

    @RequestMapping(value = "ws/get-measure-stamps", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MeasureStampResponse>> getMeasureStamps(@RequestBody ConnectionResponse connectionResponse){

        List<MeasureStampResponse> measureStampResponses = measureStampRepository.findAllByConnectionId(connectionResponse.getId()).stream().map(
                measureStamp -> new MeasureStampResponse(measureStamp.getDate(), measureStamp.getValue(),
                        measureStamp.getConnection().getId(), measureStamp.getRegister().getRegisterId())
        ).collect(Collectors.toList());

        return new ResponseEntity<List<MeasureStampResponse>>(measureStampResponses, HttpStatus.OK);
    }

}
