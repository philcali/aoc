package me.philcali.aoc.day5.year2015;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BadActors implements Predicate<String> {
    private static final Pattern BAD_ONES = Pattern.compile("ab|cd|pq|xy");

    @Override
    public boolean test(final String line) {
        final Matcher matcher = BAD_ONES.matcher(line);
        return matcher.find();
    }
}
