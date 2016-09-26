package com.milkneko.apps.utility.water.manager;

import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class ServiceShutOffManager {
    public void generateServiceShutOffs(){
        generateServiceShutOffs(new Date(new java.util.Date().getTime()));
    }

    public void generateServiceShutOffs(Date currentDate){
    }

}
