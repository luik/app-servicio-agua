package com.milkneko.apps.utility.water.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeConnectionRepository extends JpaRepository<TypeConnection, Integer> {
    TypeConnection findOneByName(String name);
}
