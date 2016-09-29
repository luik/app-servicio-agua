package com.milkneko.apps.utility.water.manager;

import com.milkneko.apps.utility.water.model.*;
import com.milkneko.apps.utility.water.response.MeasureStampResponse;
import com.milkneko.apps.utility.water.util.SeasonsUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MeasureStampManager {

    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private SeasonEntryRepository seasonEntryRepository;
    @Autowired
    private MeasureStampRepository measureStampRepository;

    public List<MeasureStampResponse> getMeasureStampOfSeason(SeasonEntry seasonEntry){

        List<MeasureStampResponse> measureStampResponses = seasonEntry.getMeasureStamp().stream().map(
                measureStamp -> MeasureStampResponse.createFrom(measureStamp)).collect(Collectors.toList());

        List<Connection> connections = connectionRepository.findAll().stream().filter(connection -> connection.isActive()).collect(Collectors.toList());
        Map<Integer, Connection> connectionsMap = new HashMap<>();
        for (Connection connection : connections){
            connectionsMap.put(connection.getId(), connection);
        }
        for(MeasureStampResponse measureStampResponse : measureStampResponses){
            connectionsMap.remove(measureStampResponse.getConnectionID());
        }
        for (Connection connection: connectionsMap.values()) {
            measureStampResponses.add(
                    new MeasureStampResponse(0, Date.valueOf(LocalDate.of(seasonEntry.getYear(), seasonEntry.getMonth(), 1)), 0,
                            connection.getId(), connection.getRegister().getRegisterId(), 0, SeasonsUtil.getSeasonEntryIdx(seasonEntry),
                            connection.getCustomer().getName(), connection.getZone().getName(),
                            connection.getAddress(), 0, true, connection.getRegister().getLastMeasureStamp().getValue()
                    ));
        }
        for (Connection connection : connections){
            if(connection.getMeasureStamps().size() < 2){
//                System.out.println("add");
//
//                measureStampResponses.add(
//                        new MeasureStampResponse(0, Date.valueOf(LocalDate.of(seasonEntry.getYear(), seasonEntry.getMonth(), 1)), 0,
//                                connection.getId(), connection.getRegister().getRegisterId(), 0, SeasonsUtil.getSeasonEntryIdx(seasonEntry),
//                                connection.getCustomer().getName(), connection.getZone().getName(),
//                                connection.getAddress(), 0, true, connection.getRegister().getInitialValue()
//                        ));
            }
        }
        return measureStampResponses;
    }

    public String parseExcelFile(int seasonEntryId, MultipartFile file) {
        String message = "OK";

        try{
            Workbook measurementsWorkbook = WorkbookFactory.create(file.getInputStream());
            int numberOfSheets = measurementsWorkbook.getNumberOfSheets();

            List<MeasureStampResponse> measureStampResponses = new ArrayList<>();

            for(int i = 0 ; i < numberOfSheets; i++){
                Sheet sheet = measurementsWorkbook.getSheetAt(i);

                int lastRow = sheet.getLastRowNum();

                for(int j = 1 ; j <= lastRow; j ++){
                    Row row = sheet.getRow(j);

                    int id = Integer.parseInt(row.getCell(0).getStringCellValue());
                    boolean isPending = row.getCell(1).getBooleanCellValue();
                    Date date = new Date(row.getCell(2).getDateCellValue().getTime());
                    int connectionId = Integer.parseInt(row.getCell(3).getStringCellValue());
                    float measure = (float) row.getCell(8).getNumericCellValue();

                    MeasureStampResponse measureStampResponse = new MeasureStampResponse(id, seasonEntryId, isPending, date, connectionId, measure);
                    measureStampResponses.add(measureStampResponse);
                }
            }

            saveMeasureStamps(measureStampResponses);
        }
        catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    public void saveMeasureStamps(List<MeasureStampResponse> measureStampResponses) {
        //int i = 0;

        for (MeasureStampResponse measureStampResponse: measureStampResponses) {
          //  System.out.println(i++);
            SeasonEntry seasonEntry = seasonEntryRepository.findOne(SeasonsUtil.createSeasonEntryKey(measureStampResponse.getSeasonEntryID()));

            if(measureStampResponse.isPending()){
                MeasureStamp measureStamp = new MeasureStamp(measureStampResponse.getDate(), measureStampResponse.getModifiedValue());
                Connection connection = connectionRepository.findOne(measureStampResponse.getConnectionID());
                measureStamp.setPrevMeasureStamp(connection.getRegister().getLastMeasureStamp());
                measureStamp.setConnection(connection);
                measureStamp.setRegister(connection.getRegister());
                connection.getRegister().setLastMeasureStamp(measureStamp);
                measureStamp.setSeasonEntry(seasonEntry);
                measureStampRepository.save(measureStamp);
            }
            else{
                MeasureStamp measureStamp = measureStampRepository.findOne(measureStampResponse.getId());
                measureStamp.setValue(measureStampResponse.getModifiedValue());;
                measureStampRepository.save(measureStamp);
            }
        }
    }
}
