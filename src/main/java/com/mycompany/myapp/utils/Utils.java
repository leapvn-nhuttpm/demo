package com.mycompany.myapp.utils;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import com.mycompany.myapp.domain.SleepTracker;

public class Utils {
    public static final Double averageTime(List<LocalTime> times) {
        List<Double> timeAsLong = times.stream().map(time -> Double.valueOf(time.toSecondOfDay())).collect(Collectors.toList());
        return Utils.average(timeAsLong);
    }
    public static final Double average(List<Double> arr) {
        return arr.stream().mapToDouble(a -> a).average().orElse(0);
    }
}
