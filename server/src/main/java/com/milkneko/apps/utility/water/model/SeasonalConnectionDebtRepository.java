package com.milkneko.apps.utility.water.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonalConnectionDebtRepository extends JpaRepository<SeasonalConnectionDebt, Integer> {
    List<SeasonalConnectionDebt> findAllByConnectionId(int connectionId);
    List<SeasonalConnectionDebt> findAllBySeasonEntryIdYearAndSeasonEntryIdMonth(int seasonYear, int seasonMonth);
}
