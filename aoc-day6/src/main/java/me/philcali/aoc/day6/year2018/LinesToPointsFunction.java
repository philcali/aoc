package me.philcali.aoc.day6.year2018;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinesToPointsFunction implements Function<List<String>, Set<Point>> {
    private static final Pattern COORDINATES = Pattern.compile("^(\\d+),\\s*(\\d+)$");

    @Override
    public Set<Point> apply(final List<String> lines) {
        final Set<Point> points = new HashSet<>();
        for (final String line : lines) {
            final Matcher matcher = COORDINATES.matcher(line);
            if (matcher.matches()) {
                points.add(new PointData(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2))));
            }
        }
        return points;
    }
}
