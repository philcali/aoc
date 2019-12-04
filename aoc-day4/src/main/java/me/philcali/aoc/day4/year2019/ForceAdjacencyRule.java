package me.philcali.aoc.day4.year2019;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class ForceAdjacencyRule implements Predicate<String> {
    private final Predicate<Integer> adjacencyCheck;

    public ForceAdjacencyRule(final Predicate<Integer> adjacencyCheck) {
        this.adjacencyCheck = adjacencyCheck;
    }

    @Override
    public boolean test(final String input) {
        final Map<Character, Integer> times = new HashMap<>();
        for (int index = 0; index < input.length() - 1; index++) {
            if (input.charAt(index) == input.charAt(index + 1)) {
                times.compute(input.charAt(index + 1), (key, number) -> {
                    if (Objects.isNull(number)) {
                        return 2;
                    } else {
                        return number + 1;
                    }
                });
            }
        }
        return times.values().stream().anyMatch(adjacencyCheck);
    }
}
