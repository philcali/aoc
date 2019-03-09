package me.philcali.aoc.day1.year2018;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(1) @Problem(2) @Year(2018)
@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent, AnnotatedDailyEvent {
    private final BiFunction<String, Long, Long> applier;

    public ProblemTwo(final BiFunction<String, Long, Long> applier) {
        this.applier = applier;
    }

    public ProblemTwo() {
        this(new LineToFrequencyFunction());
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
