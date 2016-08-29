package com.milkneko.apps.utility.water.model;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasureStampRepository extends JpaRepository<MeasureStamp, Integer> {
    List<MeasureStamp> findAllByConnectionId(int connectionId);
}
