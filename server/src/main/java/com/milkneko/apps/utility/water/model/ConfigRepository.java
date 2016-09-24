package com.milkneko.apps.utility.water.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepository extends JpaRepository<Config, Integer> {
    Config findOneByName(String name);
}
