package com.milkneko.apps.utility.water.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeasonalConnectionDebtRepository extends JpaRepository<SeasonalConnectionDebt, Integer> {
    List<SeasonalConnectionDebt> findAllByConnectionId(int connectionId);
    List<SeasonalConnectionDebt> findAllBySeasonEntryIdYearAndSeasonEntryIdMonth(int seasonYear, int seasonMonth);
}
