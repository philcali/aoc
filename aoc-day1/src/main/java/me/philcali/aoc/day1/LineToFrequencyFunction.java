package me.philcali.aoc.day1;

import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineToFrequencyFunction implements BiFunction<String, Long, Long> {
    private static final int SIGN_GROUP = 1;
    private static final int VALUE_GROUP = 2;
    private static final Pattern REGEX = Pattern.compile("([-\\+])(\\d+)");

    @Override
    public Long apply(final String line, final Long frequency) {
        final Matcher matcher = REGEX.matcher(line);
        if (matcher.find()) {
            switch (matcher.group(SIGN_GROUP)) {
            case "-":
                return frequency - Long.parseLong(matcher.group(VALUE_GROUP));
            default:
                return frequency + Long.parseLong(matcher.group(2));
            }
        }
        return frequency;
    }
}
