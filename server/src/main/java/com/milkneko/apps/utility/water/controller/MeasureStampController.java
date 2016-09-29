package com.milkneko.apps.utility.water.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.milkneko.apps.utility.water.manager.MeasureStampExcelPrinter;
import com.milkneko.apps.utility.water.manager.MeasureStampManager;
import com.milkneko.apps.utility.water.model.*;
import com.milkneko.apps.utility.water.util.SeasonsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.milkneko.apps.utility.water.response.ConnectionResponse;
import com.milkneko.apps.utility.water.response.MeasureStampResponse;
import com.milkneko.apps.utility.water.response.SeasonEntryResponse;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MeasureStampController {

    @Autowired
    private MeasureStampRepository measureStampRepository;
    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private SeasonEntryRepository seasonEntryRepository;
    @Autowired
    private MeasureStampExcelPrinter measureStampExcelPrinter;
    @Autowired
    private MeasureStampManager measureStampManager;

    @RequestMapping(value = "ws/connection/get-measure-stamps", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MeasureStampResponse>> getMeasureStampsByConnection(@RequestBody ConnectionResponse connectionResponse){

        List<MeasureStampResponse> measureStampResponses = measureStampRepository.findAllByConnectionId(connectionResponse.getId()).stream().
        filter(measureStamp -> measureStamp.getSeasonEntry() != null).map(
                measureStamp -> MeasureStampResponse.createFrom(measureStamp)).collect(Collectors.toList());

        return new ResponseEntity<List<MeasureStampResponse>>(measureStampResponses, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/season/get-measure-stamps", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MeasureStampResponse>> getMeasureStampsBySeason(@RequestBody SeasonEntryResponse seasonEntryResponse){
        SeasonEntry seasonEntry = seasonEntryRepository.findOne(SeasonsUtil.createSeasonEntryKey(seasonEntryResponse.getId()));

        List<MeasureStampResponse> measureStampResponses = measureStampManager.getMeasureStampOfSeason(seasonEntry);

        return new ResponseEntity<List<MeasureStampResponse>>(measureStampResponses, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/save-measure-stamps", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> saveMeasureStamps(@RequestBody List<MeasureStampResponse> measureStampResponses){
        measureStampManager.saveMeasureStamps(measureStampResponses);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/season/get-measure-stamps/excel/{seasonEntryId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getMeasureStampsBySeasonInExcel(@PathVariable("seasonEntryId") int seasonEntryId) {
        SeasonEntry seasonEntry = seasonEntryRepository.findOne(SeasonsUtil.createSeasonEntryKey(seasonEntryId));

        List<MeasureStampResponse> measureStampResponses = measureStampManager.getMeasureStampOfSeason(seasonEntry);

        return getResponseOfMeasureStampsExcelBytes(seasonEntry.getYear(), seasonEntry.getMonth(), measureStampResponses);
    }

    @RequestMapping(value = "ws/season/upload-measure-stamps/excel/{seasonEntryId}", method = RequestMethod.POST)
    public ResponseEntity<String> uploadMeasureStampsBySeason(@PathVariable("seasonEntryId") int seasonEntryId, @RequestParam("measurements") final MultipartFile file){
        String message = measureStampManager.parseExcelFile(seasonEntryId, file);

        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

    private ResponseEntity<byte[]> getResponseOfMeasureStampsExcelBytes(int year, int month, List<MeasureStampResponse> measureStampResponses){
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(new byte[]{}, HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            ByteArrayOutputStream byteArrayOutputStream =
                    measureStampExcelPrinter.getExcelOfMeasureStamps(measureStampResponses);

            byte[] bytes = byteArrayOutputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
            String filename = "lecturas"+ year + SeasonsUtil.getMonthName(month) + ".xlsx";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            response = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

}
