package me.philcali.aoc.day4.year2019;

import java.util.function.Predicate;

public class NeverDecreasingRule implements Predicate<String> {
    @Override
    public boolean test(final String input) {
        for (int index = 0; index < input.length() - 1; index++) {
            if (input.charAt(index + 1) < input.charAt(index)) {
                return false;
            }
        }
        return true;
    }
}
