package me.philcali.aoc.day1.year2015;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;

@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent {
    @Override
    public int day() {
        return 1;
    }

    @Override
    public int problem() {
        return 1;
    }

    @Override
    public int year() {
        return 2015;
    }

    @Override
    public void run() {
        System.out.println("Final destination: " + new Elevator().apply(streamInput()));
    }
}
