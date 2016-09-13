package com.milkneko.apps.utility.water.controller;

import com.milkneko.apps.utility.water.model.*;
import com.milkneko.apps.utility.water.response.ConnectionResponse;
import com.milkneko.apps.utility.water.response.SeasonalConnectionPaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SeasonalConnectionPaymentController {
    @Autowired
    private SeasonalConnectionPaymentRepository seasonalConnectionPaymentRepository;
    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private SeasonalConnectionDebtRepository seasonalConnectionDebtRepository;

    @RequestMapping(value = "ws/connection/get-seasonal-connection-payments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SeasonalConnectionPaymentResponse>> getSeasonalConnectionDebtsByConnection(@RequestBody ConnectionResponse connectionResponse){

        List<SeasonalConnectionPaymentResponse> seasonalConnectionPayments = seasonalConnectionDebtRepository.findAllByConnectionId(connectionResponse.getId()).
                stream().filter(seasonalConnectionDebt -> seasonalConnectionDebt.getSeasonalConnectionPayment() != null).map(
                seasonalConnectionDebt -> new SeasonalConnectionPaymentResponse(seasonalConnectionDebt.getSeasonalConnectionPayment().getId(),
                        seasonalConnectionDebt.getId(), seasonalConnectionDebt.getConnection().getId(), seasonalConnectionDebt.getIssuedDay(),
                        seasonalConnectionDebt.getInitialMeasureStamp().getValue(), seasonalConnectionDebt.getFinalMeasureStamp().getValue(),
                        seasonalConnectionDebt.getSeasonEntry().getYear(), seasonalConnectionDebt.getSeasonEntry().getMonth(),
                        seasonalConnectionDebt.getSeasonEntry().getPriceM3(), seasonalConnectionDebt.getSeasonalConnectionPayment().getDate()
                )
        ).collect(Collectors.toList());

        return new ResponseEntity<List<SeasonalConnectionPaymentResponse>>(seasonalConnectionPayments, HttpStatus.OK);
    }

    /*
    @RequestMapping(value = "ws/add-seasonal-connection-payment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addCustomer(@RequestBody SeasonalConnectionPaymentResponse seasonalConnectionPaymentResponse){
        SeasonalConnectionPayment seasonalConnectionPayment = new SeasonalConnectionPayment(seasonalConnectionPaymentResponse.getPaymentDate());
        SeasonalConnectionDebt seasonalConnectionDebt = seasonalConnectionDebtRepository.findOne(seasonalConnectionPaymentResponse.getSeasonalConnectionDebtId());
        seasonalConnectionPayment.setSeasonalConnectionDebt(seasonalConnectionDebt);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    */
}
