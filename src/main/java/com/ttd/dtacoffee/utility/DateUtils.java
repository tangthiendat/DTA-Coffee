package com.ttd.dtacoffee.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class DateUtils {

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
