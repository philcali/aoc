package me.philcali.aoc.day5.year2015;

import java.util.function.Predicate;

public class DoubleOccurance implements Predicate<String> {
    @Override
    public boolean test(final String line) {
        char lastLetter = '0';
        for (char letter : line.toCharArray()) {
            if (letter == lastLetter) {
                return true;
            }
            lastLetter = letter;
        }
        return false;
    }
}
