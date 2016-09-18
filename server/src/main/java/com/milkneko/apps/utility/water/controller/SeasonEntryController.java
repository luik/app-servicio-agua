package com.milkneko.apps.utility.water.controller;

import com.milkneko.apps.utility.water.model.SeasonEntry;
import com.milkneko.apps.utility.water.model.SeasonEntryKey;
import com.milkneko.apps.utility.water.model.SeasonEntryRepository;
import com.milkneko.apps.utility.water.response.SeasonEntryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SeasonEntryController {

    @Autowired
    private SeasonEntryRepository seasonEntryRepository;

    @RequestMapping(value = "ws/get-season-entries", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SeasonEntryResponse>> getSeasonEntries(){
        Instant startTime = Instant.now();

        List<SeasonEntryResponse> seasonEntries = seasonEntryRepository.findAllOrderByYearMonth().stream().map(
                seasonEntry -> new SeasonEntryResponse(seasonEntry.getYear(), seasonEntry.getMonth(), seasonEntry.getPriceM3())
        ).collect(Collectors.toList());

        System.out.println("get seasons : " + Duration.between(startTime, Instant.now()));

        return new ResponseEntity<List<SeasonEntryResponse>>(seasonEntries, HttpStatus.OK);
    }
    
    @RequestMapping(value = "ws/update-season-entry", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateSeasonEntry(@RequestBody SeasonEntryResponse seasonEntryResponse){
    	SeasonEntry seasonEntry = seasonEntryRepository.findOne(new SeasonEntryKey(seasonEntryResponse.getYear(), seasonEntryResponse.getMonth()));
    	seasonEntry.setPriceM3(seasonEntryResponse.getPriceM3());
    	seasonEntryRepository.save(seasonEntry);
    	
    	return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

}
