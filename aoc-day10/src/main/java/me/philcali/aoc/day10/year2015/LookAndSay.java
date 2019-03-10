package me.philcali.aoc.day10.year2015;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class LookAndSay implements Function<String, String> {
    @Override
    public String apply(final String input) {
        final List<List<Integer>> parts = new LinkedList<>();
        List<Integer> currentMarker = Arrays.asList(0, 0);
        for (final char letter : input.toCharArray()) {
            final int number = letter - 48;
            if (number != currentMarker.get(0)) {
                currentMarker = Arrays.asList(number, 1);
                parts.add(currentMarker);
            } else {
                currentMarker.set(1, currentMarker.get(1) + 1);
            }
        }
        final StringBuilder builder = new StringBuilder();
        parts.forEach(part -> {
            builder.append(part.get(1)).append(part.get(0));
        });
        return builder.toString();
    }
}
