package me.philcali.aoc.common;

public interface DailyEvent extends Comparable<DailyEvent>, Runnable {
    int day();

    int problem();

    @Override
    default int compareTo(final DailyEvent other) {
        return Integer.valueOf(day()).compareTo(other.day())
                + Integer.valueOf(problem()).compareTo(other.problem());
    }
}
