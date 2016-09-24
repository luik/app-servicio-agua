package com.milkneko.apps.utility.water.controller;

import com.milkneko.apps.utility.water.model.Config;
import com.milkneko.apps.utility.water.model.ConfigRepository;
import com.milkneko.apps.utility.water.response.ConfigResponse;
import com.milkneko.apps.utility.water.response.ConnectionTypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ConfigController {

    @Autowired
    private ConfigRepository configRepository;

    @RequestMapping(value = "ws/get-configs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConfigResponse>> getConfigs(){
        List<ConfigResponse> configResponses = configRepository.findAll().stream().map(
                config -> ConfigResponse.createFrom(config)
        ).collect(Collectors.toList());

        return new ResponseEntity<List<ConfigResponse>>(configResponses, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/update-config", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateConfig(@RequestBody ConfigResponse configResponse){
        Config config = configRepository.getOne(configResponse.getId());

        config.setValue(configResponse.getValue());
        configRepository.save(config);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

}
