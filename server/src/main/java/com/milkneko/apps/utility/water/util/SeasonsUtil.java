package com.milkneko.apps.utility.water.util;

import com.milkneko.apps.utility.water.model.SeasonEntryKey;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SeasonsUtil {
    public static Date getFirstDayOfSeasonIdx(int seasonIndex){
        SeasonEntryKey seasonEntryKey = createSeasonEntryKey(seasonIndex);
        return new Date(new GregorianCalendar(
                seasonEntryKey.getYear(), seasonEntryKey.getMonth() - 1, 1).getTime().getTime());
    }

    public static Date getLastDayOfSeasonIdx(int seasonIndex){
        SeasonEntryKey seasonEntryKey = createSeasonEntryKey(seasonIndex);
        int lastDay = new GregorianCalendar(seasonEntryKey.getYear(),
                seasonEntryKey.getMonth() - 1, 1).getActualMaximum(Calendar.DAY_OF_MONTH);

        return new Date(
                new GregorianCalendar(seasonEntryKey.getYear(), seasonEntryKey.getMonth() - 1, lastDay).getTime().getTime());
    }

    public static SeasonEntryKey createSeasonEntryKey(int seasonIndex){
        int year = seasonIndex/12 + 2016;
        int month = seasonIndex%12;

        return new SeasonEntryKey(year, month);
    }
}
