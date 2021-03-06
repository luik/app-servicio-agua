package com.milkneko.apps.utility.water.manager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

@Component
public class SeasonalConnectionDebtPDFPrinter {

    @Autowired
    private MeasureStampRepository measureStampRepository;

    public ByteArrayOutputStream getPDFOfSeasonalConnectionDebts(List<SeasonalConnectionDebt> seasonalConnectionDebts)
            throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        DateFormat dateFormat = new SimpleDateFormat("dd - MM - yyyy");
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        PdfFont font = PdfFontFactory.createFont(IOUtils.toByteArray(classloader.getResourceAsStream("static/NotoSansCJKtc-Light.otf")), "Identity-H" , true);

        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        ImageData background = ImageDataFactory.create(IOUtils.toByteArray(classloader.getResourceAsStream("static/recibo.png")));
        Image backgroundImage = new Image(background);

        for (SeasonalConnectionDebt seasonalConnectionDebt: seasonalConnectionDebts) {

            SeasonalConnectionDebtResponse seasonalConnectionDebtResponse = SeasonalConnectionDebtResponse.createFrom(seasonalConnectionDebt);

            String name = seasonalConnectionDebt.getConnection().getCustomer().getName();
            String recibo = String.format("%09d", seasonalConnectionDebt.getId());
            String connection = String.format("%09d", seasonalConnectionDebtResponse.getConnectionId());
            String issueDate = dateFormat.format(seasonalConnectionDebtResponse.getIssuedDate());
            String dueDate = dateFormat.format(seasonalConnectionDebtResponse.getDueDate());
            String address = seasonalConnectionDebt.getConnection().getAddress();
            String zone = seasonalConnectionDebt.getConnection().getZone().getName();
            String documentId = seasonalConnectionDebt.getConnection().getCustomer().getDocumentId();
            String registerId = seasonalConnectionDebt.getConnection().getRegister().getRegisterId();
            String categoryName = seasonalConnectionDebt.getConnection().getConnectionType().getName();

            String prevMeasurementValue = String.format("%.2f", seasonalConnectionDebt.getMeasureStamp().getPrevMeasureStamp().getValue());
            String prevMeasurementDate = dateFormat.format(seasonalConnectionDebt.getMeasureStamp().getPrevMeasureStamp().getDate());
            String finalMeasurementValue = String.format("%.2f", seasonalConnectionDebt.getMeasureStamp().getValue());
            String finalMeasurementDate = dateFormat.format(seasonalConnectionDebt.getMeasureStamp().getDate());

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

            float[] previousMeasurements = new float[monthsToDraw];
            int initialIdx = previousMeasurements.length + 1 - measureStampList.size();

            if(initialIdx < 0){
                initialIdx = 0;
            }

            for(int j = initialIdx; j < monthsToDraw; j++){
                previousMeasurements[j] = measureStampList.get(j + 1 - initialIdx).getValue() - measureStampList.get(j - initialIdx).getValue();
            }

            PdfPage pdfPage = pdfDocument.addNewPage(PageSize.A5);

            PdfCanvas pdfCanvas = new PdfCanvas(pdfPage);

            Canvas canvas = new Canvas(pdfCanvas, pdfDocument, pdfPage.getPageSize());
            Canvas backgroundCanvas = new Canvas(pdfCanvas, pdfDocument, pdfPage.getPageSize());

            backgroundCanvas.add(backgroundImage);
            pdfCanvas.saveState().beginText().moveText(50, 486).setFontAndSize(font, 7).showText(name).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(62, 511).setFontAndSize(font, 7).showText(recibo).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(337, 465).setFontAndSize(font, 12).showText(connection).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(93, 501).setFontAndSize(font, 7).showText(issueDate).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(288, 501).setFontAndSize(font, 8).showText(totalDebtStr).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(288, 510).setFontAndSize(font, 8).showText(dueDate).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(53, 474).setFontAndSize(font, 7).showText(address).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(48, 458).setFontAndSize(font, 7).showText(zone).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(52, 449).setFontAndSize(font, 7).showText(documentId).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(27, 394).setFontAndSize(font, 7).showText(registerId).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(251, 431).setFontAndSize(font, 7).showText(categoryName).endText().restoreState();

            pdfCanvas.saveState().beginText().moveText(80, 394).setFontAndSize(font, 7).showText(prevMeasurementValue).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(125, 394).setFontAndSize(font, 7).showText(prevMeasurementDate).endText().restoreState();

            pdfCanvas.saveState().beginText().moveText(188, 394).setFontAndSize(font, 7).showText(finalMeasurementValue).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(226, 394).setFontAndSize(font, 7).showText(finalMeasurementDate).endText().restoreState();

            pdfCanvas.saveState().beginText().moveText(285, 394).setFontAndSize(font, 7).showText(measuresDelta).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(373, 394).setFontAndSize(font, 7).showText(measuresDelta).endText().restoreState();

            pdfCanvas.saveState().beginText().moveText(80, 384).setFontAndSize(font, 7).showText(prevMeasurementDate).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(136, 384).setFontAndSize(font, 7).showText(finalMeasurementDate).endText().restoreState();

            pdfCanvas.saveState().beginText().moveText(22, 356).setFontAndSize(font, 8).showText(codeService).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(50, 356).setFontAndSize(font, 8).showText(descriptionService).endText().restoreState();

            pdfCanvas.saveState().beginText().moveText(22, 346).setFontAndSize(font, 8).showText(codeDrainService).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(50, 346).setFontAndSize(font, 8).showText(descriptionDrainService).endText().restoreState();

            pdfCanvas.saveState().beginText().moveText(22, 336).setFontAndSize(font, 8).showText(codeFixedCharge).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(50, 336).setFontAndSize(font, 8).showText(descriptionFixedCharge).endText().restoreState();

            pdfCanvas.saveState().beginText().moveText(22, 326).setFontAndSize(font, 8).showText(codeConnectionCharge).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(50, 326).setFontAndSize(font, 8).showText(descriptionConnectionCharge).endText().restoreState();

            canvas.setFont(font).setFontSize(7).showTextAligned(serviceDebt, 403, 356, TextAlignment.RIGHT);
            canvas.setFont(font).setFontSize(7).showTextAligned(drainServiceDebt, 403, 346, TextAlignment.RIGHT);
            canvas.setFont(font).setFontSize(7).showTextAligned(fixedChargeDebt, 403, 336, TextAlignment.RIGHT);
            canvas.setFont(font).setFontSize(7).showTextAligned(connectionChargeDebt, 403, 326, TextAlignment.RIGHT);


            canvas.setFont(font).setFontSize(7).showTextAligned(subtotalDebtStr, 403, 253, TextAlignment.RIGHT);
            canvas.setFont(font).setFontSize(7).showTextAligned(igvDebtStr, 403, 243, TextAlignment.RIGHT);
            canvas.setFont(font).setFontSize(7).showTextAligned(roundStr, 403, 233, TextAlignment.RIGHT);
            canvas.setFont(font).setFontSize(7).showTextAligned(totalDebtStr, 403, 223, TextAlignment.RIGHT);

            canvas.setFont(font).setFontSize(10).showTextAligned(totalDebtStr, 403, 205, TextAlignment.RIGHT);

            for (int j = 0; j < monthsToDraw; j++) {
                pdfCanvas.saveState().setLineWidth(7f).moveTo(110 + 14*j, 205).lineTo(110 + 14*j, 205 + 0.8*previousMeasurements[j]).stroke().restoreState();
            }

            pdfCanvas.saveState().setLineWidth(0.5f).moveTo(110 - 14 - 5, 205).lineTo(110 + 14*6, 205).stroke().restoreState();
            canvas.setFont(font).setFontSize(7).showTextAligned("0", 100f - 14f, (float) (205f + 0.*25f - 5), TextAlignment.RIGHT);
            pdfCanvas.saveState().setLineWidth(0.25f).setLineDash(1f, 1f).moveTo(110 - 14 - 5, 205 + 0.8*25).lineTo(110 + 14*6, 205 + 0.8*25).stroke().restoreState();
            canvas.setFont(font).setFontSize(7).showTextAligned("25", 100f - 14f, (float) (205f + 0.8*25f - 5), TextAlignment.RIGHT);
            pdfCanvas.saveState().setLineWidth(0.25f).setLineDash(1f, 1f).moveTo(110 - 14 - 5, 205 + 0.8*50).lineTo(110 + 14*6, 205 + 0.8*50).stroke().restoreState();
            canvas.setFont(font).setFontSize(7).showTextAligned("50", 100f - 14f, (float) (205f + 0.8*50f - 5), TextAlignment.RIGHT);
            pdfCanvas.saveState().setLineWidth(0.25f).setLineDash(1f, 1f).moveTo(110 - 14 - 5, 205 + 0.8*75).lineTo(110 + 14*6, 205 + 0.8*75).stroke().restoreState();
            canvas.setFont(font).setFontSize(7).showTextAligned("75", 100f - 14f, (float) (205f + 0.8*75f - 5), TextAlignment.RIGHT);
            pdfCanvas.saveState().setLineWidth(0.5f).moveTo(110 - 14 - 5, 205 + 0.8*100).lineTo(110 + 14*6, 205 + 0.8*100).stroke().restoreState();
            canvas.setFont(font).setFontSize(7).showTextAligned("100", 100f - 14f, (float) (205f + 0.8*100f - 5), TextAlignment.RIGHT);

            pdfCanvas.saveState().setLineWidth(0.5f).moveTo(110 - 14, 205).lineTo(110 - 14, 205 + 0.8*100).stroke().restoreState();
            pdfCanvas.saveState().setLineWidth(0.5f).moveTo(110 + 14*6, 205).lineTo(110 + 14*6, 205 + 0.8*100).stroke().restoreState();

            pdfCanvas.saveState().beginText().moveText(32, 182).setFontAndSize(font, 7).showText(CantLetras.convertNumberToLetter(totalDebtStr)).endText().restoreState();

            pdfCanvas.saveState().beginText().moveText(141, 101).setFontAndSize(font, 7).showText(connection).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(141, 93).setFontAndSize(font, 7).showText(name).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(141, 85).setFontAndSize(font, 7).showText("GRUPO IV").endText().restoreState();

            canvas.setFont(font).setFontSize(10).showTextAligned(totalDebtStr, 236, 64, TextAlignment.LEFT);

            pdfCanvas.saveState().beginText().moveText(58, 41).setFontAndSize(font, 7).showText(recibo).endText().restoreState();
            pdfCanvas.saveState().beginText().moveText(58, 33).setFontAndSize(font, 7).showText(issueDate).endText().restoreState();

            BarcodeQRCode barcode = new BarcodeQRCode(UUID.randomUUID().toString());
            pdfCanvas.saveState().addXObject(barcode.createFormXObject(Color.BLACK, 3, pdfDocument), 300, 10).restoreState();
        }

        pdfDocument.close();
        return byteArrayOutputStream;
    }

}
