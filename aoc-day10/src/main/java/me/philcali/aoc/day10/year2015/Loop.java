package me.philcali.aoc.day10.year2015;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Loop implements BiFunction<String, Integer, String> {
    private final Function<String, String> lookAndSay;

    public Loop(final Function<String, String> lookAndSay) {
        this.lookAndSay = lookAndSay;
    }

    @Override
    public String apply(final String input, final Integer times) {
        int index = 0;
        String temp = input;
        for (; index < times; index++) {
            temp = lookAndSay.apply(temp);
        }
        return temp;
    }
}
