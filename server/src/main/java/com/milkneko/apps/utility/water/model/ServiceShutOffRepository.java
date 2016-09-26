package com.milkneko.apps.utility.water.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceShutOffRepository extends JpaRepository<ServiceShutOff, Integer> {
    ServiceShutOff findOneByConnectionId(int connectionId);
}
