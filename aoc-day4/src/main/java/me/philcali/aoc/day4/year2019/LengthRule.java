package me.philcali.aoc.day4.year2019;

import java.util.function.Predicate;

public class LengthRule implements Predicate<String> {
    @Override
    public boolean test(String input) {
        return input.length() == 6;
    }
}
