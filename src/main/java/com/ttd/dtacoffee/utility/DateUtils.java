package com.ttd.dtacoffee.utility;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class DateUtils {
    public static List<LocalDate> getCurrentWeekDays() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfCurrentWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate firstDayOfNextWeek = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate currentDay = firstDayOfCurrentWeek;
        List<LocalDate> currentWeekDays = new ArrayList<>();
        while (currentDay.isBefore(firstDayOfNextWeek)) {
            currentWeekDays.add(currentDay);
            currentDay = currentDay.plusDays(1);
        }
        return currentWeekDays;
    }

    public static List<LocalDate> getCurrentMonthDays() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate currentDay = firstDayOfMonth;
        List<LocalDate> currentMonthDays = new ArrayList<>();
        while (!currentDay.isAfter(lastDayOfMonth)) {
            currentMonthDays.add(currentDay);
            currentDay = currentDay.plusDays(1);
        }
        return currentMonthDays;
    }


}
