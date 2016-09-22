package com.milkneko.apps.utility.water.controller;

import com.milkneko.apps.utility.water.model.ConnectionType;
import com.milkneko.apps.utility.water.model.ConnectionTypeRepository;
import com.milkneko.apps.utility.water.response.ConnectionTypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@RestController
public class ConnectionTypeController {

    @Autowired
    private ConnectionTypeRepository connectionTypeRepository;

    @RequestMapping(value = "ws/get-connection-types", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConnectionTypeResponse>> getTypeConnections(){
        List<ConnectionType> connectionTypes = connectionTypeRepository.findAll();
        List<ConnectionTypeResponse> connectionTypeResponses = new ArrayList<>();

        for (ConnectionType connectionType : connectionTypes) {
            connectionTypeResponses.addAll(ConnectionTypeResponse.createFrom(connectionType));
        }

        return new ResponseEntity<List<ConnectionTypeResponse>>(connectionTypeResponses, HttpStatus.OK);
    }

    @RequestMapping(value = "ws/update-connection-type", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateRegister(@RequestBody ConnectionTypeResponse connectionTypeResponse){
        ConnectionType connectionType = connectionTypeRepository.getOne(connectionTypeResponse.getId());

        String[] pricesM3 = connectionType.getPricesM3().split(";");
        String[] pricesDrain = connectionType.getPricesDrain().split(";");
        pricesM3[connectionTypeResponse.getIdx()] = Float.toString(connectionTypeResponse.getWaterServicePrice());
        pricesDrain[connectionTypeResponse.getIdx()] = Float.toString(connectionTypeResponse.getDrainServicePrice());
        StringJoiner stringJoiner = new StringJoiner(";");
        for (String priceM3: pricesM3) {
            stringJoiner.add(priceM3);
        }
        connectionType.setPricesM3(stringJoiner.toString());

        stringJoiner = new StringJoiner(";");
        for (String priceDrain: pricesDrain) {
            stringJoiner.add(priceDrain);
        }
        connectionType.setPricesDrain(stringJoiner.toString());

        connectionType.setFixedCharge(connectionTypeResponse.getFixedCharge());
        connectionType.setConnectionCharge(connectionTypeResponse.getConnectionCharge());
        connectionType.setConnectionChargeDuration(connectionTypeResponse.getConnectionChargeDuration());
        
        connectionTypeRepository.save(connectionType);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

}
