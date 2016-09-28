package com.milkneko.apps.utility.water.manager;

import com.milkneko.apps.utility.water.model.Connection;
import com.milkneko.apps.utility.water.model.ConnectionRepository;
import com.milkneko.apps.utility.water.model.MeasureStamp;
import com.milkneko.apps.utility.water.model.SeasonEntry;
import com.milkneko.apps.utility.water.response.MeasureStampResponse;
import com.milkneko.apps.utility.water.util.SeasonsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MeasureStampManager {

    @Autowired
    private ConnectionRepository connectionRepository;

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
                System.out.println("add");

                measureStampResponses.add(
                        new MeasureStampResponse(0, Date.valueOf(LocalDate.of(seasonEntry.getYear(), seasonEntry.getMonth(), 1)), 0,
                                connection.getId(), connection.getRegister().getRegisterId(), 0, SeasonsUtil.getSeasonEntryIdx(seasonEntry),
                                connection.getCustomer().getName(), connection.getZone().getName(),
                                connection.getAddress(), 0, true, connection.getRegister().getInitialValue()
                        ));
            }
        }
        return measureStampResponses;
    }
}
