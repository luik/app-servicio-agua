package com.milkneko.apps.utility.water.manager;

import com.milkneko.apps.utility.water.model.MeasureStamp;
import com.milkneko.apps.utility.water.model.MeasureStampRepository;
import com.milkneko.apps.utility.water.model.SeasonalConnectionDebt;
import com.milkneko.apps.utility.water.response.MeasureStampResponse;
import com.milkneko.apps.utility.water.response.SeasonalConnectionDebtResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MeasureStampExcelPrinter {

    @Autowired
    private MeasureStampRepository measureStampRepository;

    public ByteArrayOutputStream getExcelOfMeasureStamps(List<MeasureStampResponse> measureStampResponses)
            throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        DateFormat dateFormat = new SimpleDateFormat("dd - MM - yyyy");
        Workbook newWorkbook = new XSSFWorkbook();
        CellStyle cellDateStyle = newWorkbook.createCellStyle();
        CreationHelper createHelper = newWorkbook.getCreationHelper();
        cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yy-mm-dd"));

        Map<String, List<MeasureStampResponse>> zoneName2measureStampsMap = new HashMap<>();

        for (MeasureStampResponse measureStampResponse :
                measureStampResponses) {
            if(!zoneName2measureStampsMap.containsKey(measureStampResponse.getZoneName())){
                zoneName2measureStampsMap.put(measureStampResponse.getZoneName(), new ArrayList<>());
            }
            zoneName2measureStampsMap.get(measureStampResponse.getZoneName()).add(measureStampResponse);
        }

        for (String zoneName :
                zoneName2measureStampsMap.keySet()) {
            Sheet sheet = newWorkbook.createSheet(zoneName);

            sheet.setColumnWidth(0, 2500);
            sheet.setColumnWidth(1, 2500);
            sheet.setColumnWidth(2, 2500);
            sheet.setColumnWidth(3, 13000);
            sheet.setColumnWidth(4, 13000);
            sheet.setColumnWidth(5, 2500);
            sheet.setColumnWidth(6, 2500);

            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Fecha lectura");
            row.createCell(1).setCellValue("Número de conexión");
            row.createCell(2).setCellValue("Número de medidor");
            row.createCell(3).setCellValue("Beneficiario");
            row.createCell(4).setCellValue("Dirección");
            row.createCell(5).setCellValue("Lectura anterior");
            row.createCell(6).setCellValue("Lectura actual");

            List<MeasureStampResponse> zoneMeasureStampResponses = zoneName2measureStampsMap.get(zoneName);

            for(int i = 0; i < zoneMeasureStampResponses.size(); i++){
                row = sheet.createRow(i + 1);

                MeasureStampResponse measureStampResponse = zoneMeasureStampResponses.get(i);

                row.createCell(0).setCellValue(measureStampResponse.getDate());
                row.createCell(1).setCellValue( String.format("%06d", measureStampResponse.getConnectionID()));
                row.createCell(2).setCellValue(measureStampResponse.getRegisterID());
                row.createCell(3).setCellValue(measureStampResponse.getCustomerName());
                row.createCell(4).setCellValue(measureStampResponse.getAddress());
                row.createCell(5).setCellValue(measureStampResponse.getPrevValue());
                row.createCell(6).setCellValue(measureStampResponse.getValue());

                row.getCell(0).setCellStyle(cellDateStyle);
                row.getCell(1).setCellType(CellType.STRING);
                row.getCell(2).setCellType(CellType.STRING);
                row.getCell(3).setCellType(CellType.STRING);
                row.getCell(4).setCellType(CellType.STRING);
                row.getCell(5).setCellType(CellType.NUMERIC);
                row.getCell(6).setCellType(CellType.NUMERIC);
            }
        }

        newWorkbook.write(byteArrayOutputStream);
        return byteArrayOutputStream;
    }

}
