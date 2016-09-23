package com.milkneko.apps.utility.water.manager;

import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.TextAlignment;
import com.milkneko.apps.utility.water.model.MeasureStamp;
import com.milkneko.apps.utility.water.model.MeasureStampRepository;
import com.milkneko.apps.utility.water.model.SeasonalConnectionDebt;
import com.milkneko.apps.utility.water.response.SeasonalConnectionDebtResponse;
import com.milkneko.apps.utility.water.util.CantLetras;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class SeasonalConnectionDebtExcelPrinter {

    @Autowired
    private MeasureStampRepository measureStampRepository;

    public ByteArrayOutputStream getExcelOfSeasonalConnectionDebts(List<SeasonalConnectionDebt> seasonalConnectionDebts)
            throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        DateFormat dateFormat = new SimpleDateFormat("dd - MM - yyyy");

        Workbook newWorkbook = new XSSFWorkbook();
        Sheet reportSheet = newWorkbook.createSheet("Reporte");

        reportSheet.setColumnWidth(0, 3000);
        reportSheet.setColumnWidth(1, 15000);
        reportSheet.setColumnWidth(2, 8000);
        reportSheet.setColumnWidth(3, 6000);
        reportSheet.setColumnWidth(4, 4000);
        reportSheet.setColumnWidth(5, 4000);
        reportSheet.setColumnWidth(6, 4000);
        reportSheet.setColumnWidth(7, 4000);
        reportSheet.setColumnWidth(8, 4000);
        reportSheet.setColumnWidth(9, 4000);
        reportSheet.setColumnWidth(10, 4000);
        reportSheet.setColumnWidth(11, 4000);
        reportSheet.setColumnWidth(12, 4000);
        reportSheet.setColumnWidth(13, 4000);
        reportSheet.setColumnWidth(14, 4000);
        reportSheet.setColumnWidth(15, 4000);

        int currentRow = 0;
        {
            Row row = reportSheet.createRow(currentRow);
            row.createCell(0).setCellValue("DNI/RUC");
            row.createCell(1).setCellValue("Beneficiario");
            row.createCell(2).setCellValue("Dirección");
            row.createCell(3).setCellValue("Sector");
            row.createCell(4).setCellValue("Número conexión");
            row.createCell(5).setCellValue("Número medidor");
            row.createCell(6).setCellValue("Número recibo");
            row.createCell(7).setCellValue("Fecha lectura anterior");
            row.createCell(8).setCellValue("Fecha lectura actual");
            row.createCell(9).setCellValue("Lectura anterior");
            row.createCell(10).setCellValue("Lectura actual");
            row.createCell(11).setCellValue("Consumo");
            row.createCell(12).setCellValue("Total a pagar");
            row.createCell(13).setCellValue("Número de pago");
            row.createCell(14).setCellValue("Fecha de pago");
            row.createCell(15).setCellValue("Pagado");

            currentRow++;
        }

        float totalDebtSum = 0;
        float totalPaymentSum = 0;
        
        for (SeasonalConnectionDebt seasonalConnectionDebt: seasonalConnectionDebts) {

            SeasonalConnectionDebtResponse seasonalConnectionDebtResponse = SeasonalConnectionDebtResponse.createFrom(seasonalConnectionDebt);

            String name = seasonalConnectionDebt.getConnection().getCustomer().getName();
            String recibo = String.format("%09d", seasonalConnectionDebt.getId());
            String connection = String.format("%09d", seasonalConnectionDebtResponse.getConnectionId());
            String issueDate = dateFormat.format(seasonalConnectionDebtResponse.getIssuedDate());
            String address = seasonalConnectionDebt.getConnection().getAddress();
            String zone = seasonalConnectionDebt.getConnection().getZone().getName();
            String documentId = seasonalConnectionDebt.getConnection().getCustomer().getDocumentId();
            String registerId = seasonalConnectionDebt.getConnection().getRegister().getRegisterId();
            String categoryName = seasonalConnectionDebt.getConnection().getConnectionType().getName();

            String prevMeasurementValue = String.format("%.2f", seasonalConnectionDebt.getInitialMeasureStamp().getValue());
            String prevMeasurementDate = dateFormat.format(seasonalConnectionDebt.getInitialMeasureStamp().getDate());
            String finalMeasurementValue = String.format("%.2f", seasonalConnectionDebt.getFinalMeasureStamp().getValue());
            String finalMeasurementDate = dateFormat.format(seasonalConnectionDebt.getFinalMeasureStamp().getDate());

            String measuresDelta = String.format("%.2f", seasonalConnectionDebtResponse.getDeltaMeasurements());
            String codeService = "001";
            String descriptionService = "SERVICIO DE AGUA";

            String codeDrainService = "008";
            String descriptionDrainService = "SERVICIO DE DESAGUE";

            String codeFixedCharge = "407";
            String descriptionFixedCharge = "CARGO FIJO";

            String codeConnectionCharge = "12";
            String descriptionConnectionCharge = "CONEXIÓN DOMICILIARIA (24 meses)";

            float igvDebt = seasonalConnectionDebtResponse.getIGVDebtValue();
            float subtotalDebt = seasonalConnectionDebtResponse.getDebtValue();
            float totalDebt = seasonalConnectionDebtResponse.getTotalDebtRoundedValue();
            float round = seasonalConnectionDebtResponse.getRoundValue();

            String serviceDebt = String.format("%.2f", seasonalConnectionDebtResponse.getWaterServiceDebt());
            String drainServiceDebt = String.format("%.2f", seasonalConnectionDebtResponse.getDrainPrice());
            String fixedChargeDebt = String.format("%.2f", seasonalConnectionDebtResponse.getFixedCharge());
            String connectionChargeDebt = String.format("%.2f", seasonalConnectionDebtResponse.getConnectionCharge());

            String subtotalDebtStr = String.format("%.2f", subtotalDebt);
            String igvDebtStr = String.format("%.2f", igvDebt);
            String roundStr = String.format("%.2f", round);
            String totalDebtStr = String.format("%.2f", totalDebt);

            int monthsToDraw = 6;

            LocalDate endDateSearch = LocalDate.of(seasonalConnectionDebt.getSeasonEntry().getYear(), seasonalConnectionDebt.getSeasonEntry().getMonth(),
                    LocalDate.of(seasonalConnectionDebt.getSeasonEntry().getYear(), seasonalConnectionDebt.getSeasonEntry().getMonth(), 1).lengthOfMonth());
            LocalDate startDateSearch = endDateSearch.minusMonths(monthsToDraw + 1);

            List<MeasureStamp> measureStampList = this.measureStampRepository.findAllByConnectionIdAndDateBetweenOrderByDate(seasonalConnectionDebtResponse.getConnectionId(),
                    Date.valueOf(startDateSearch), Date.valueOf(endDateSearch));

            float[] previouseMeasurements = new float[monthsToDraw];
            int initialIdx = previouseMeasurements.length + 1 - measureStampList.size();
            for(int j = initialIdx; j < monthsToDraw; j++){
                previouseMeasurements[j] = measureStampList.get(j + 1 - initialIdx).getValue() - measureStampList.get(j - initialIdx).getValue();
            }

            Row row = reportSheet.createRow(currentRow);
            row.createCell(0).setCellValue(documentId);
            row.createCell(1).setCellValue(name);
            row.createCell(2).setCellValue(address);
            row.createCell(3).setCellValue(zone);
            row.createCell(4).setCellValue(connection);
            row.createCell(5).setCellValue(registerId);
            row.createCell(6).setCellValue(recibo);
            row.createCell(7).setCellValue(prevMeasurementDate);
            row.createCell(8).setCellValue(finalMeasurementDate);
            row.createCell(9).setCellValue(prevMeasurementValue);
            row.createCell(10).setCellValue(finalMeasurementValue);
            row.createCell(11).setCellValue(measuresDelta);
            row.createCell(12).setCellValue(totalDebtStr);
            if(seasonalConnectionDebt.getSeasonalConnectionPayment() != null){
                row.createCell(13).setCellValue(seasonalConnectionDebt.getSeasonalConnectionPayment().getId());
                String paymentDate = dateFormat.format(seasonalConnectionDebt.getSeasonalConnectionPayment().getDate());
                row.createCell(14).setCellValue(paymentDate);
                row.createCell(15).setCellValue(totalDebtStr);
                
                totalPaymentSum += totalDebt;
            }else {
                row.createCell(13).setCellValue("N/C");
                row.createCell(14).setCellValue("N/C");
            }

            
            currentRow++;
            
            totalDebtSum += totalDebt;

        }
        
        Row row = reportSheet.createRow(currentRow + 1);
        row.createCell(12).setCellValue(String.format("%.2f", totalDebtSum));
        row.createCell(15).setCellValue(String.format("%.2f", totalPaymentSum));

        newWorkbook.write(byteArrayOutputStream);
        return byteArrayOutputStream;
    }

}
