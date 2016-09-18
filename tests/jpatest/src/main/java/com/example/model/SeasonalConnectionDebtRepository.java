package com.example.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeasonalConnectionDebtRepository extends JpaRepository<SeasonalConnectionDebt, Integer> {
    List<SeasonalConnectionDebt> findAllByConnectionId(int connectionId);
    List<SeasonalConnectionDebt> findAllBySeasonEntryIdYearAndSeasonEntryIdMonth(int seasonYear, int seasonMonth);
}
