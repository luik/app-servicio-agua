package com.milkneko.apps.utility.water.controller;

import com.milkneko.apps.utility.water.manager.SeasonalConnectionDebtExcelPrinter;
import com.milkneko.apps.utility.water.manager.SeasonalConnectionDebtManager;
import com.milkneko.apps.utility.water.manager.SeasonalConnectionDebtPDFPrinter;
import com.milkneko.apps.utility.water.model.*;
import com.milkneko.apps.utility.water.response.ConnectionResponse;
import com.milkneko.apps.utility.water.response.SeasonEntryResponse;
import com.milkneko.apps.utility.water.response.SeasonalConnectionDebtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SeasonalConnectionDebtController {

    @Autowired
    private SeasonalConnectionDebtRepository seasonalConnectionDebtRepository;
    @Autowired
    private SeasonalConnectionPaymentRepository seasonalConnectionPaymentRepository;
    @Autowired
    private ConnectionRepository connectionRepository;
	@Autowired
	private SeasonalConnectionDebtManager seasonalConnectionDebtManager;
	@Autowired
	private SeasonalConnectionDebtPDFPrinter seasonalConnectionDebtPDFPrinter;
    @Autowired
    private SeasonalConnectionDebtExcelPrinter seasonalConnectionDebtExcelPrinter;

    @RequestMapping(value = "ws/connection/get-seasonal-connection-debts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SeasonalConnectionDebtResponse>> getSeasonalConnectionDebtsByConnection(@RequestBody ConnectionResponse connectionResponse){

        List<SeasonalConnectionDebtResponse> seasonalConnectionDebts = seasonalConnectionDebtRepository.findAllByConnectionId(connectionResponse.getId()).stream().map(
                seasonalConnectionDebt -> SeasonalConnectionDebtResponse.createFrom(seasonalConnectionDebt)).collect(Collectors.toList());

        return new ResponseEntity<List<SeasonalConnectionDebtResponse>>(seasonalConnectionDebts, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/season/get-seasonal-connection-debts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SeasonalConnectionDebtResponse>> getSeasonalConnectionDebtsBySeason(@RequestBody SeasonEntryResponse seasonEntryResponse){
        int index = seasonEntryResponse.getId();
        int year = index/12 + 2016;
        int month = index%12 - 1;

        List<SeasonalConnectionDebtResponse> seasonalConnectionDebts = seasonalConnectionDebtRepository.findAllBySeasonEntryIdYearAndSeasonEntryIdMonth(year, month + 1).stream().map(
                seasonalConnectionDebt -> SeasonalConnectionDebtResponse.createFrom(seasonalConnectionDebt)).collect(Collectors.toList());

        return new ResponseEntity<List<SeasonalConnectionDebtResponse>>(seasonalConnectionDebts, HttpStatus.OK);
    }

    @RequestMapping(value = "/ws/season/generate-seasonal-connection-debts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> generateSeasonalConnectionDebtsBySeason(@RequestBody SeasonEntryResponse seasonEntryResponse) {
		Instant startTime = Instant.now();

    	int index = seasonEntryResponse.getId();

        seasonalConnectionDebtManager.generateSeasonalConnectionDebtsBySeason(index);

		System.out.println("generate seasons debts: " + Duration.between(startTime, Instant.now()));

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/season/get-seasonal-connection-debts/pdf/{seasonEntryId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getSeasonalConnectionDebtsBySeasonInPdf(@PathVariable("seasonEntryId") int seasonEntryId) {
        int year = seasonEntryId/12 + 2016;
        int month = seasonEntryId%12;
        List<SeasonalConnectionDebt> seasonalConnectionDebts = seasonalConnectionDebtRepository.findAllBySeasonEntryIdYearAndSeasonEntryIdMonth(year, month);

        return getResponseOfSeasonalConnectionDebtsPdfBytes(seasonalConnectionDebts);
    }

    @RequestMapping(value = "ws/season/get-seasonal-connection-debts/excel/{seasonEntryId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getSeasonalConnectionDebtsBySeasonInExcel(@PathVariable("seasonEntryId") int seasonEntryId) {
        int year = seasonEntryId/12 + 2016;
        int month = seasonEntryId%12;
        List<SeasonalConnectionDebt> seasonalConnectionDebts = seasonalConnectionDebtRepository.findAllBySeasonEntryIdYearAndSeasonEntryIdMonth(year, month);

        return getResponseOfSeasonalConnectionDebtsExcelBytes(seasonalConnectionDebts);
    }

    @RequestMapping(value = "ws/connection/get-seasonal-connection-debts/pdf/{connectionId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getSeasonalConnectionDebtsByConnectionInPdf(@PathVariable("connectionId") int connectionId) {

    	Connection connection = connectionRepository.findOne(connectionId);
    	
    	List<SeasonalConnectionDebt> seasonalConnectionDebts = new ArrayList<>(connection.getSeasonalConnectionDebts());
    	
    	return getResponseOfSeasonalConnectionDebtsPdfBytes(seasonalConnectionDebts);
    }

    private ResponseEntity<byte[]> getResponseOfSeasonalConnectionDebtsPdfBytes(List<SeasonalConnectionDebt> seasonalConnectionDebts){
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(new byte[]{}, HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			ByteArrayOutputStream byteArrayOutputStream =
					seasonalConnectionDebtPDFPrinter.getPDFOfSeasonalConnectionDebts(seasonalConnectionDebts);

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

    private ResponseEntity<byte[]> getResponseOfSeasonalConnectionDebtsExcelBytes(List<SeasonalConnectionDebt> seasonalConnectionDebts){
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(new byte[]{}, HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            ByteArrayOutputStream byteArrayOutputStream =
                    seasonalConnectionDebtExcelPrinter.getExcelOfSeasonalConnectionDebts(seasonalConnectionDebts);

            byte[] bytes = byteArrayOutputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
            String filename = "reporte.xlsx";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            response = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

	@RequestMapping(value = "ws/update-seasonal-connection-debt", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> updateSeasonalConnectionDebt(@RequestBody SeasonalConnectionDebtResponse seasonalConnectionDebtResponse){
        SeasonalConnectionDebt seasonalConnectionDebt = seasonalConnectionDebtRepository.findOne(seasonalConnectionDebtResponse.getId());

        if(seasonalConnectionDebt.getSeasonalConnectionPayment() == null){
            SeasonalConnectionPayment seasonalConnectionPayment = new SeasonalConnectionPayment(new Date(new java.util.Date().getTime()));
            seasonalConnectionPayment.setSeasonalConnectionDebt(seasonalConnectionDebt);

            seasonalConnectionPaymentRepository.save(seasonalConnectionPayment);
        }

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

}
