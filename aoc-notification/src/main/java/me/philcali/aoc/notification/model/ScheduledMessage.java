package me.philcali.aoc.notification.model;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;

@Data @Builder
public interface ScheduledMessage {
    int getYear();

    int getDay();

    default int yearOrCurrent(final int currentYear) {
        return getYear() == 0 ? currentYear : getYear();
    }

    default int dayOrCurrent(final int currentDay) {
        return getDay() == 0 ? currentDay : getDay();
    }
}
