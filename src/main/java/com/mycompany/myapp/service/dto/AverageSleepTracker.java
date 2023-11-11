package com.mycompany.myapp.service.dto;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.mycompany.myapp.domain.SleepTracker;
import com.mycompany.myapp.utils.Utils;

public class AverageSleepTracker {
    private LocalTime sleepTime;
    private LocalTime wakeupTime;
    private Double totalSleepDuration;

    public AverageSleepTracker(List<SleepTracker> sleepTrackers) {
        List<LocalTime> listSleepTime = sleepTrackers.stream().map(SleepTracker::getSleepTime).filter(Objects::nonNull).collect(Collectors.toList());
        List<LocalTime> listWakeupTime = sleepTrackers.stream().map(SleepTracker::getWakeupTime).filter(Objects::nonNull).collect(Collectors.toList());
        List<Double> listSleepDuration = sleepTrackers.stream().map(SleepTracker::getTotalSleepDuration).filter(Objects::nonNull).collect(Collectors.toList());
        this.sleepTime = LocalTime.ofSecondOfDay(Math.round(Utils.averageTime(listSleepTime)));
        this.wakeupTime = LocalTime.ofSecondOfDay(Math.round(Utils.averageTime(listWakeupTime)));
        this.totalSleepDuration = Utils.average(listSleepDuration);
    }

    public LocalTime getSleepTime() {
        return sleepTime;
    }
    public void setSleepTime(LocalTime sleepTime) {
        this.sleepTime = sleepTime;
    }
    public LocalTime getWakeupTime() {
        return wakeupTime;
    }
    public void setWakeupTime(LocalTime wakeupTime) {
        this.wakeupTime = wakeupTime;
    }

    public Double getTotalSleepDuration() {
        return totalSleepDuration;
    }

    public void setTotalSleepDuration(Double totalSleepDuration) {
        this.totalSleepDuration = totalSleepDuration;
    }
}
