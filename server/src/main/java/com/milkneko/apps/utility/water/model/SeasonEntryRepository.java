package com.milkneko.apps.utility.water.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeasonEntryRepository extends JpaRepository<SeasonEntry, Integer> {
    @Query("SELECT s FROM SeasonEntry s ORDER BY year, month")
    List<SeasonEntry> findAllOrderByYearMonth();
}
