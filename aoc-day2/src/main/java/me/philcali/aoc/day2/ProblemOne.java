package me.philcali.aoc.day2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;

@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent {
    @Override
    public int day() {
        return 2;
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
        final List<AtomicLong> collector = Arrays.asList(new AtomicLong(), new AtomicLong());
        for (final String line : readLines()) {
            final Map<Character, Integer> letters = new HashMap<>();
            for (final Character letter : line.toCharArray()) {
                letters.compute(letter, (existingLetter, times) -> {
                    return Objects.isNull(times) ? 1 : times + 1;
                });
            }
            final Map<Integer, Integer> lookups = new HashMap<>();
            lookups.put(2, 0);
            lookups.put(3, 1);
            letters.forEach((letter, times) -> {
                Optional.ofNullable(lookups.remove(times)).ifPresent(index -> {
                    collector.get(index).incrementAndGet();
                });
            });
        }
        System.out.println("Total " + collector.stream().map(AtomicLong::get).reduce((twos, threes) -> twos * threes));
    }
}
