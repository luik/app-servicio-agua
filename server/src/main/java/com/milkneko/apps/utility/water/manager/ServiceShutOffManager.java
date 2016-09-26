package com.milkneko.apps.utility.water.manager;

import com.milkneko.apps.utility.water.model.*;
import com.milkneko.apps.utility.water.response.SeasonalConnectionDebtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServiceShutOffManager {
    @Autowired
    private ServiceShutOffRepository serviceShutOffRepository;
    @Autowired
    private ConnectionRepository connectionRepository;

    public void generateServiceShutOffs(){
        generateServiceShutOffs(new Date(new java.util.Date().getTime()));
    }

    public void generateServiceShutOffs(Date currentDate){
        List<Connection> connections = connectionRepository.findAll().stream().filter(connection -> connection.isActive()).collect(Collectors.toList());

        for (Connection connection :
                connections) {
            SeasonalConnectionPayment lastSeasonalConnectionPayment = connection.getLastSeasonalConnectionPayment();
            if(lastSeasonalConnectionPayment != null){
                SeasonalConnectionDebt nextSeasonalConnectionDebt = lastSeasonalConnectionPayment.getSeasonalConnectionDebt().getNextSeasonalConnectionDebt();
                if(nextSeasonalConnectionDebt != null){
                    if(nextSeasonalConnectionDebt.getDueDate().compareTo(currentDate) < 0){
                        createOrUpdateServiceShutOff(connection, nextSeasonalConnectionDebt, currentDate);
                    }
                }
            }else{
                List<SeasonalConnectionDebt> seasonalConnectionDebts = connection.getSeasonalConnectionDebts().stream().sorted((o1, o2) -> o1.getDueDate().compareTo(o2.getDueDate())).collect(Collectors.toList());
                for (SeasonalConnectionDebt seasonalConnectionDebt :
                        seasonalConnectionDebts) {
                    if(seasonalConnectionDebt.getDueDate().compareTo(currentDate) < 0){
                        createOrUpdateServiceShutOff(connection, seasonalConnectionDebt, currentDate);
                    }
                }
            }
        }
    }

    private void createOrUpdateServiceShutOff(Connection connection, SeasonalConnectionDebt seasonalConnectionDebt, Date currentDate){
        ServiceShutOff serviceShutOff = serviceShutOffRepository.findOneByConnectionId(connection.getId());
        if(serviceShutOff == null){
            SeasonalConnectionDebtResponse seasonalConnectionDebtResponse = SeasonalConnectionDebtResponse.createFrom(seasonalConnectionDebt);

            serviceShutOff = new ServiceShutOff(null, currentDate, seasonalConnectionDebtResponse.getTotalDebtRoundedValue(), currentDate);
            serviceShutOff.setSeasonalConnectionDebt(seasonalConnectionDebt);
            serviceShutOff.setConnection(connection);

            serviceShutOffRepository.save(serviceShutOff);
        }
    }

}
