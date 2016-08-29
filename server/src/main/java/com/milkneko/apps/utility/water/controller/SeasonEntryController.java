package com.milkneko.apps.utility.water.controller;

import com.milkneko.apps.utility.water.model.SeasonEntryRepository;
import com.milkneko.apps.utility.water.model.ZoneRepository;
import com.milkneko.apps.utility.water.response.SeasonEntryResponse;
import com.milkneko.apps.utility.water.response.ZoneResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SeasonEntryController {

    @Autowired
    private SeasonEntryRepository seasonEntryRepository;

    @RequestMapping(value = "ws/get-season-entries", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SeasonEntryResponse>> getSeasonEntries(){
        List<SeasonEntryResponse> seasonEntries = seasonEntryRepository.findAll().stream().map(
                seasonEntry -> new SeasonEntryResponse(seasonEntry.getYear(), seasonEntry.getMonth(), seasonEntry.getPriceM3())
        ).collect(Collectors.toList());
        return new ResponseEntity<List<SeasonEntryResponse>>(seasonEntries, HttpStatus.OK);
    }

}
