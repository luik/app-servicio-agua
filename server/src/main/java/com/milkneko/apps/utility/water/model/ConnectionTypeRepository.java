package com.milkneko.apps.utility.water.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionTypeRepository extends JpaRepository<ConnectionType, Integer> {
    ConnectionType findOneByName(String name);
}
