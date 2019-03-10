package me.philcali.aoc.day5.year2015;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ThreeVowels implements Predicate<String> {
    private static final Pattern VOWELS = Pattern.compile("[aeiou]");

    @Override
    public boolean test(final String line) {
        final String replaced = VOWELS.matcher(line).replaceAll("");
        return line.length() - replaced.length() >= 3;
    }
}
