package me.philcali.aoc.notification.model;

import me.philcali.zero.lombok.annotation.Data;

@Data
public interface ScheduledMessage {
    int year();

    int day();

    default int yearOrCurrent(final int currentYear) {
        return year() == 0 ? currentYear : year();
    }

    default int dayOrCurrent(final int currentDay) {
        return day() == 0 ? currentDay : day();
    }
}
