package com.mycompany.myapp.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static final Double averageTime(List<LocalTime> times) {
        List<Double> timeAsLong = times.stream().map(time -> Double.valueOf(time.toSecondOfDay())).collect(Collectors.toList());
        return Utils.average(timeAsLong);
    }
    public static final Double average(List<Double> arr) {
        return arr.stream().mapToDouble(a -> a).average().orElse(0);
    }

    public static final LocalDate getStartDateOfWeek() {
        LocalDate today = LocalDate.now();
        LocalDate monday = today;
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY)
        {
            monday = monday.minusDays(1);
        }
        return monday;
    }

    public static final LocalDate getEndDateOfWeek() {
        LocalDate today = LocalDate.now();
        LocalDate sunday = today;
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY)
        {
            sunday = sunday.plusDays(1);
        }
        return sunday;

    }
}
