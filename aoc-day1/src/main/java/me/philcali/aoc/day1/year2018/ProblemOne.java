package me.philcali.aoc.day1.year2018;

import java.util.function.BiFunction;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(1) @Problem(1) @Year(2018)
@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent, AnnotatedDailyEvent {
    private final BiFunction<String, Long, Long> applier;

    public ProblemOne(final BiFunction<String, Long, Long> applier) {
        this.applier = applier;
    }

    public ProblemOne() {
        this(new LineToFrequencyFunction());
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
