package com.milkneko.apps.utility.water.controller;

import com.milkneko.apps.utility.water.manager.SeasonalConnectionPaymentPrinter;
import com.milkneko.apps.utility.water.model.*;
import com.milkneko.apps.utility.water.response.ConnectionResponse;
import com.milkneko.apps.utility.water.response.SeasonEntryResponse;
import com.milkneko.apps.utility.water.response.SeasonalConnectionPaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
    @Autowired
    SeasonalConnectionPaymentPrinter seasonalConnectionPaymentPrinter;

    @RequestMapping(value = "ws/connection/get-seasonal-connection-payments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SeasonalConnectionPaymentResponse>> getSeasonalConnectionDebtsByConnection(@RequestBody ConnectionResponse connectionResponse){

        List<SeasonalConnectionDebt> seasonalConnectionDebts = seasonalConnectionDebtRepository.findAllByConnectionId(connectionResponse.getId());

        List<SeasonalConnectionPaymentResponse> seasonalConnectionPayments = seasonalConnectionDebtRepository.findAllByConnectionId(connectionResponse.getId()).
                stream().filter(seasonalConnectionDebt -> seasonalConnectionDebt.getSeasonalConnectionPayment() != null).map(
                seasonalConnectionDebt -> SeasonalConnectionPaymentResponse.createFrom(seasonalConnectionDebt)).collect(Collectors.toList());

        return new ResponseEntity<List<SeasonalConnectionPaymentResponse>>(seasonalConnectionPayments, HttpStatus.OK);
    }

    @RequestMapping(value = "/ws/season/generate-seasonal-connection-payments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SeasonalConnectionPaymentResponse>> getSeasonalConnectionPaymentsBySeason(@RequestBody SeasonEntryResponse seasonEntryResponse) {
        int index = seasonEntryResponse.getId();
        int year = index/12 + 2016;
        int month = index%12 - 1;

        List<SeasonalConnectionPaymentResponse> seasonalConnectionPayments = seasonalConnectionDebtRepository.findAllBySeasonEntryIdYearAndSeasonEntryIdMonth(year, month + 1).
                stream().filter(seasonalConnectionDebt -> seasonalConnectionDebt.getSeasonalConnectionPayment() != null).map(
                seasonalConnectionDebt -> SeasonalConnectionPaymentResponse.createFrom(seasonalConnectionDebt)).collect(Collectors.toList());

        return new ResponseEntity<List<SeasonalConnectionPaymentResponse>>(seasonalConnectionPayments, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/get-seasonal-connection-payment/pdf/{seasonalConnectionDebtId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getSeasonalConnectionDebtsByConnectionInPdf(@PathVariable("seasonalConnectionDebtId") int seasonalConnectionDebtId) {

        SeasonalConnectionDebt seasonalConnectionDebt = seasonalConnectionDebtRepository.findOne(seasonalConnectionDebtId);
        SeasonalConnectionPayment seasonalConnectionPayment = seasonalConnectionDebt.getSeasonalConnectionPayment();

        List<SeasonalConnectionPayment> seasonalConnectionPayments = new ArrayList<>();
        seasonalConnectionPayments.add(seasonalConnectionPayment);

        return getResponseOfSeasonalConnectionPaymentsPdfBytes(seasonalConnectionPayments);
    }

    private ResponseEntity<byte[]> getResponseOfSeasonalConnectionPaymentsPdfBytes(List<SeasonalConnectionPayment> seasonalConnectionDebts){
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(new byte[]{}, HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            ByteArrayOutputStream byteArrayOutputStream =
                    seasonalConnectionPaymentPrinter.getPDFOfSeasonalConnectionPayments(seasonalConnectionDebts);

            byte[] bytes = byteArrayOutputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            //String filename = "recibos.pdf";
            //headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            response = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
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
