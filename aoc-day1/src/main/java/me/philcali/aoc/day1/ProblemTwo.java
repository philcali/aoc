package me.philcali.aoc.day1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;

@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent {
    private final BiFunction<String, Long, Long> applier;

    public ProblemTwo(final BiFunction<String, Long, Long> applier) {
        this.applier = applier;
    }

    public ProblemTwo() {
        this(new LineToFrequencyFunction());
    }

    @Override
    public int day() {
        return 1;
    }

    @Override
    public int problem() {
        return 2;
    }

    @Override
    public void run() {
        System.out.println("Finding the first duplicate frequency, starting with 0!");
        System.out.println(findDuplicateFrequency());
    }

    private long findDuplicateFrequency() {
        long frequency = 0L;
        final Set<Long> seenFrequencies = new HashSet<>(Arrays.asList(frequency));
        do {
            for (final String line : readLines()) {
                frequency = applier.apply(line, frequency);
                if (!seenFrequencies.add(frequency)) {
                    return frequency;
                }
            }
        } while (true);
    }
}
