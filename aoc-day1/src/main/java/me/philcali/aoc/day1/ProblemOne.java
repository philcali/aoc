package me.philcali.aoc.day1;

import java.util.function.BiFunction;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;

@AutoService(DailyEvent.class)
public class ProblemOne implements DailyEvent, DailyInputEvent {
    private final BiFunction<String, Long, Long> applier;

    public ProblemOne(final BiFunction<String, Long, Long> applier) {
        this.applier = applier;
    }

    public ProblemOne() {
        this(new LineToFrequencyFunction());
    }

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
        return 2018;
    }

    @Override
    public void run() {
        System.out.println("Adding all frequencies together, starting with 0!");
        long frequency = 0;
        for (String line : readLines()) {
            frequency = applier.apply(line, frequency);
        }
        System.out.println(frequency);
    }
}
