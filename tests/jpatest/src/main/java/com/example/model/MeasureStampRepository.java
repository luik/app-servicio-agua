package com.example.model;


import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface MeasureStampRepository extends JpaRepository<MeasureStamp, Integer> {
    List<MeasureStamp> findAllByConnectionId(int connectionId);
    MeasureStamp findOneByConnectionIdAndDateBetweenOrderByDate(int connectionId, Date startDate, Date endDate);
    List<MeasureStamp> findAllByConnectionIdAndDateBetweenOrderByDate(int connectionId, Date startDate, Date endDate);
    List<MeasureStamp> findByDateBetween(Date startDate, Date endDate);
}
