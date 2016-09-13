package com.milkneko.apps.utility.water.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.property.TextAlignment;
import com.milkneko.apps.utility.water.model.Connection;
import com.milkneko.apps.utility.water.model.ConnectionRepository;
import com.milkneko.apps.utility.water.model.MeasureStamp;
import com.milkneko.apps.utility.water.model.MeasureStampRepository;
import com.milkneko.apps.utility.water.model.SeasonEntry;
import com.milkneko.apps.utility.water.model.SeasonEntryKey;
import com.milkneko.apps.utility.water.model.SeasonEntryRepository;
import com.milkneko.apps.utility.water.model.SeasonalConnectionDebt;
import com.milkneko.apps.utility.water.model.SeasonalConnectionDebtRepository;
import com.milkneko.apps.utility.water.response.ConnectionResponse;
import com.milkneko.apps.utility.water.response.SeasonEntryResponse;
import com.milkneko.apps.utility.water.response.SeasonalConnectionDebtResponse;

@RestController
public class SeasonalConnectionDebtController {

    @Autowired
    private SeasonalConnectionDebtRepository seasonalConnectionDebtRepository;
    @Autowired
    private MeasureStampRepository measureStampRepository;
    @Autowired
    private SeasonEntryRepository seasonEntryRepository;
    @Autowired
    private ConnectionRepository connectionRepository;

    @RequestMapping(value = "ws/connection/get-seasonal-connection-debts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SeasonalConnectionDebtResponse>> getSeasonalConnectionDebtsByConnection(@RequestBody ConnectionResponse connectionResponse){

        List<SeasonalConnectionDebtResponse> seasonalConnectionDebts = seasonalConnectionDebtRepository.findAllByConnectionId(connectionResponse.getId()).stream().map(
                seasonalConnectionDebt -> new SeasonalConnectionDebtResponse(seasonalConnectionDebt.getId(), seasonalConnectionDebt.getConnection().getId(), seasonalConnectionDebt.getIssuedDay(),
                        seasonalConnectionDebt.getInitialMeasureStamp().getValue(), seasonalConnectionDebt.getFinalMeasureStamp().getValue(),
                        seasonalConnectionDebt.getSeasonEntry().getYear(), seasonalConnectionDebt.getSeasonEntry().getMonth(),
                        seasonalConnectionDebt.getSeasonEntry().getPriceM3()
                )
        ).collect(Collectors.toList());

        return new ResponseEntity<List<SeasonalConnectionDebtResponse>>(seasonalConnectionDebts, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/season/get-seasonal-connection-debts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SeasonalConnectionDebtResponse>> getSeasonalConnectionDebtsBySeason(@RequestBody SeasonEntryResponse seasonEntryResponse){
        int index = seasonEntryResponse.getId();
        int year = index/12 + 2016;
        int month = index%12 - 1;

        List<SeasonalConnectionDebtResponse> seasonalConnectionDebts = seasonalConnectionDebtRepository.findAllBySeasonEntryIdYearAndSeasonEntryIdMonth(year, month + 1).stream().map(
                seasonalConnectionDebt -> new SeasonalConnectionDebtResponse(seasonalConnectionDebt.getId(), seasonalConnectionDebt.getConnection().getId(), seasonalConnectionDebt.getIssuedDay(),
                        seasonalConnectionDebt.getInitialMeasureStamp().getValue(), seasonalConnectionDebt.getFinalMeasureStamp().getValue(),
                        seasonalConnectionDebt.getSeasonEntry().getYear(), seasonalConnectionDebt.getSeasonEntry().getMonth(),
                        seasonalConnectionDebt.getSeasonEntry().getPriceM3()
                )
        ).collect(Collectors.toList());

        return new ResponseEntity<List<SeasonalConnectionDebtResponse>>(seasonalConnectionDebts, HttpStatus.OK);
    }

    @RequestMapping(value = "/ws/season/generate-seasonal-connection-debts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> generateSeasonalConnectionDebtsBySeason(@RequestBody SeasonEntryResponse seasonEntryResponse) {
    	long initialTime = new java.util.Date().getTime();

    	int index = seasonEntryResponse.getId();
        int year = index/12 + 2016;
        int month = index%12 - 1;
        int lastDay = new GregorianCalendar(year, month, 1).getActualMaximum(Calendar.DAY_OF_MONTH);

        int prevYear = (index - 1)/12 + 2016;
        int prevMonth = (index - 1)%12 - 1;
        int prevLastDay = new GregorianCalendar(prevYear, prevMonth, 1).getActualMaximum(Calendar.DAY_OF_MONTH);

        Date startDate = new Date(new GregorianCalendar(year, month, 1).getTime().getTime());
        Date endDate = new Date(new GregorianCalendar(year, month, lastDay).getTime().getTime());

        Date prevStartDate = new Date(new GregorianCalendar(prevYear, prevMonth, 1).getTime().getTime());
        Date prevEndDate = new Date(new GregorianCalendar(prevYear, prevMonth, prevLastDay).getTime().getTime());

        List<MeasureStamp> measureStamps = measureStampRepository.findByDateBetween(startDate, endDate);

        for (MeasureStamp measureStamp : measureStamps) {
        	if(measureStamp.getCurrentSeasonalConnectionDebt() != null)
        	{
        		continue;
        	}

            SeasonEntry seasonEntry = seasonEntryRepository.findOne(new SeasonEntryKey(year, month + 1));

            MeasureStamp prevMeasureStamp = measureStampRepository.findOneByConnectionIdAndDateBetweenOrderByDate(measureStamp.getConnection().getId(), prevStartDate, prevEndDate);

            SeasonalConnectionDebt seasonalConnectionDebt = new SeasonalConnectionDebt(new Date(new java.util.Date().getTime()));
            seasonalConnectionDebt.setConnection(measureStamp.getConnection());
            seasonalConnectionDebt.setSeasonEntry(seasonEntry);
            seasonalConnectionDebt.setInitialMeasureStamp(prevMeasureStamp);
            seasonalConnectionDebt.setFinalMeasureStamp(measureStamp);

            seasonalConnectionDebtRepository.save(seasonalConnectionDebt);
        }

        long finalTime = new java.util.Date().getTime();

        System.out.println("generate seasonal connection debts time: " + (finalTime - initialTime));

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/season/get-seasonal-connection-debts/pdf/{seasonEntryId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getSeasonalConnectionDebtsBySeasonInPdf(@PathVariable("seasonEntryId") int seasonEntryId) {
        int year = seasonEntryId/12 + 2016;
        int month = seasonEntryId%12;
        List<SeasonalConnectionDebt> seasonalConnectionDebts = seasonalConnectionDebtRepository.findAllBySeasonEntryIdYearAndSeasonEntryIdMonth(year, month);


        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(new byte[]{}, HttpStatus.INTERNAL_SERVER_ERROR);
        try {
        	
        	ByteArrayOutputStream byteArrayOutputStream = getPDFOfSeasonalConnectionDebts(seasonalConnectionDebts);

            byte[] bytes = byteArrayOutputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            //String filename = "recibos.pdf";
            //headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            response = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        }

        return response;
    }

    @RequestMapping(value = "ws/connection/get-seasonal-connection-debts/pdf/{connectionId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getSeasonalConnectionDebtsByConnectionInPdf(@PathVariable("connectionId") int connectionId) {

    	Connection connection = connectionRepository.findOne(connectionId);
    	
    	List<SeasonalConnectionDebt> seasonalConnectionDebts = new ArrayList<>(connection.getSeasonalConnectionDebts());
    	
    	ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(new byte[]{}, HttpStatus.INTERNAL_SERVER_ERROR);
    	try {
    		
    		ByteArrayOutputStream byteArrayOutputStream = getPDFOfSeasonalConnectionDebts(seasonalConnectionDebts);
    		
    		byte[] bytes = byteArrayOutputStream.toByteArray();
    		
    		HttpHeaders headers = new HttpHeaders();
    		headers.setContentType(MediaType.parseMediaType("application/pdf"));
    		//String filename = "recibos.pdf";
    		//headers.setContentDispositionFormData(filename, filename);
    		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    		response = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    	} catch (IOException e) {
    		e.printStackTrace();
    		return response;
    	}
    	
    	return response;
    }

	private ByteArrayOutputStream getPDFOfSeasonalConnectionDebts(List<SeasonalConnectionDebt> seasonalConnectionDebts)
			throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		DateFormat dateFormat = new SimpleDateFormat("dd - MM - yyyy");
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		PdfFont font = PdfFontFactory.createFont(IOUtils.toByteArray(classloader.getResourceAsStream("static/NotoSansCJKtc-Light.otf")), "Identity-H" , true);
		
		PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);

		PdfDocument pdfDocument = new PdfDocument(pdfWriter);
		ImageData background = ImageDataFactory.create(IOUtils.toByteArray(classloader.getResourceAsStream("static/recibo.png")));

		///int i = 0;

		for (SeasonalConnectionDebt seasonalConnectionDebt: seasonalConnectionDebts) {
		    ///if(i > 10) break;

		    SeasonalConnectionDebtResponse seasonalConnectionDebtResponse =
		    		new SeasonalConnectionDebtResponse(seasonalConnectionDebt.getId(), seasonalConnectionDebt.getConnection().getId(),
		    				seasonalConnectionDebt.getIssuedDay(), seasonalConnectionDebt.getInitialMeasureStamp().getValue(),
		    				seasonalConnectionDebt.getFinalMeasureStamp().getValue(), seasonalConnectionDebt.getSeasonEntry().getYear(),
		    				seasonalConnectionDebt.getSeasonEntry().getMonth(), seasonalConnectionDebt.getSeasonEntry().getPriceM3()
		            );

		    String name = seasonalConnectionDebt.getConnection().getCustomer().getName();
		    String recibo = String.format("%09d", seasonalConnectionDebt.getId());
		    String connection = String.format("%09d", seasonalConnectionDebtResponse.getConnectionId());
		    String issueDate = dateFormat.format(seasonalConnectionDebtResponse.getIssuedDate());
		    String serviceDebt = String.format("%.2f", seasonalConnectionDebtResponse.getDebtValue());
		    String address = seasonalConnectionDebt.getConnection().getAddress();
		    String zone = seasonalConnectionDebt.getConnection().getZone().getName();
		    String documentId = seasonalConnectionDebt.getConnection().getCustomer().getDocumentId();
		    String registerId = seasonalConnectionDebt.getConnection().getRegister().getRegisterId();

		    String prevMeasurementValue = String.format("%.2f", seasonalConnectionDebt.getInitialMeasureStamp().getValue());
		    String prevMeasurementDate = dateFormat.format(seasonalConnectionDebt.getInitialMeasureStamp().getDate());
		    String finalMeasurementValue = String.format("%.2f", seasonalConnectionDebt.getFinalMeasureStamp().getValue());
		    String finalMeasurementDate = dateFormat.format(seasonalConnectionDebt.getFinalMeasureStamp().getDate());

		    String measuresDelta = String.format("%.2f", seasonalConnectionDebtResponse.getDeltaMeasurements());
		    String codeService = "001";
		    String descriptionService = "SERVICIO DE AGUA";
		    float igvDebt = 0.18f*seasonalConnectionDebtResponse.getDebtValue();
		    float totalDebt = 1.18f*seasonalConnectionDebtResponse.getDebtValue();
		    float round = ((Math.round(totalDebt*100))%5)*-1/100f;
		    totalDebt += round;

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
		    
		    /*
		    for (int j = 0; j < measureStampList.size(); j++) {
		    	MeasureStamp measureStamp = measureStampList.get(j);
		        System.out.println(dateFormat.format(measureStamp.getDate()) + " " + measureStamp.getValue());
		    }
		    for (int j = 0; j < monthsToDraw; j++) {
		        System.out.println(previouseMeasurements[j]);
		    }
		    System.out.println("---------------");
		    */
		    
		    PdfPage pdfPage = pdfDocument.addNewPage(PageSize.A5);

		    PdfCanvas pdfCanvas = new PdfCanvas(pdfPage);
		    Canvas canvas = new Canvas(pdfCanvas, pdfDocument, pdfPage.getPageSize());
		    pdfCanvas.addImage(background, pdfPage.getPageSize(), true);
		    pdfCanvas.saveState().beginText().moveText(50, 486).setFontAndSize(font, 7).showText(name).endText().restoreState();
		    pdfCanvas.saveState().beginText().moveText(62, 511).setFontAndSize(font, 7).showText(recibo).endText().restoreState();
		    pdfCanvas.saveState().beginText().moveText(337, 465).setFontAndSize(font, 12).showText(connection).endText().restoreState();
		    pdfCanvas.saveState().beginText().moveText(93, 501).setFontAndSize(font, 7).showText(issueDate).endText().restoreState();
		    pdfCanvas.saveState().beginText().moveText(278, 501).setFontAndSize(font, 8).showText(serviceDebt).endText().restoreState();
		    pdfCanvas.saveState().beginText().moveText(53, 474).setFontAndSize(font, 7).showText(address).endText().restoreState();
		    pdfCanvas.saveState().beginText().moveText(48, 458).setFontAndSize(font, 7).showText(zone).endText().restoreState();
		    pdfCanvas.saveState().beginText().moveText(52, 449).setFontAndSize(font, 7).showText(documentId).endText().restoreState();
		    pdfCanvas.saveState().beginText().moveText(27, 394).setFontAndSize(font, 7).showText(registerId).endText().restoreState();

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

		    canvas.setFont(font).setFontSize(7).showTextAligned(serviceDebt, 403, 356, TextAlignment.RIGHT);
		    canvas.setFont(font).setFontSize(7).showTextAligned(serviceDebt, 403, 253, TextAlignment.RIGHT);
		    canvas.setFont(font).setFontSize(7).showTextAligned(igvDebtStr, 403, 243, TextAlignment.RIGHT);
		    canvas.setFont(font).setFontSize(7).showTextAligned(roundStr, 403, 233, TextAlignment.RIGHT);
		    canvas.setFont(font).setFontSize(7).showTextAligned(totalDebtStr, 403, 223, TextAlignment.RIGHT);

		    canvas.setFont(font).setFontSize(10).showTextAligned(totalDebtStr, 403, 205, TextAlignment.RIGHT);
		    
		    for (int j = 0; j < monthsToDraw; j++) {
		        System.out.println(previouseMeasurements[j]);
		        
		        pdfCanvas.saveState().setLineWidth(7f).moveTo(110 + 14*j, 205).lineTo(110 + 14*j, 205 + 0.8*previouseMeasurements[j]).stroke().restoreState();
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
		    
		    pdfCanvas.saveState().beginText().moveText(141, 101).setFontAndSize(font, 7).showText(connection).endText().restoreState();
		    pdfCanvas.saveState().beginText().moveText(141, 93).setFontAndSize(font, 7).showText(name).endText().restoreState();
		    pdfCanvas.saveState().beginText().moveText(141, 85).setFontAndSize(font, 7).showText("GRUPO IV").endText().restoreState();
		    
		    canvas.setFont(font).setFontSize(10).showTextAligned(totalDebtStr, 236, 64, TextAlignment.LEFT);
		    
		    pdfCanvas.saveState().beginText().moveText(58, 41).setFontAndSize(font, 7).showText(recibo).endText().restoreState();
		    pdfCanvas.saveState().beginText().moveText(58, 33).setFontAndSize(font, 7).showText(issueDate).endText().restoreState();
		    
		    ///i++;
		}

		pdfDocument.close();
		return byteArrayOutputStream;
	}

}
