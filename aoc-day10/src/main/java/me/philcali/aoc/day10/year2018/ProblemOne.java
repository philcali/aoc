package me.philcali.aoc.day10.year2018;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Description;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@AutoService(DailyEvent.class)
@Year(2018) @Day(10) @Problem(1)
@Description("The Stars Align")
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    private static final Pattern STAR_PATTERN = Pattern.compile("position=<\\s*(-?\\d+),\\s*(-?\\d+)> velocity=<\\s*(-?\\d+),\\s*(-?\\d+)>");

    @Override
    public void run() {
        int starId = 0;
        final Map<Star, Coordinates> starMap = new HashMap<>();
        for (final String line : readLines()) {
            final Matcher matcher = STAR_PATTERN.matcher(line);
            if (matcher.matches()) {
                final Star star = StarData.builder()
                        .id(++starId)
                        .velocity(new VelocityData(
                                Integer.parseInt(matcher.group(3)),
                                Integer.parseInt(matcher.group(4))))
                        .build();
                starMap.put(star, new CoordinatesData(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2))));
            }
        }
        int iteration = 0;
        while (!atLeastOneNeighbor(starMap)) {
            if (iteration % 1000 == 0) {
                System.out.println("Fast forwarded: " + iteration + " secounds");
            }
            starMap.keySet().forEach(star -> {
                starMap.compute(star, (s, coord) -> s.move(coord));
            });
            iteration++;
        }
        System.out.println("Stars aligned at: " + iteration);
        int minRows = Integer.MAX_VALUE;
        int maxRows = 0;
        int minCols = Integer.MAX_VALUE;
        int maxCols = 0;
        for (final Coordinates coord : starMap.values()) {
            minCols = Math.min(minCols, coord.x());
            maxCols = Math.max(maxCols, coord.x());
            minRows = Math.min(minRows, coord.y());
            maxRows = Math.max(maxRows, coord.y());
        }
        printStarImage(starMap, maxRows, minRows, maxCols, minCols);
    }

    private boolean atLeastOneNeighbor(final Map<Star, Coordinates> starMap) {
        return starMap.size() == starMap.values().stream()
                .filter(coord -> starMap.values().stream()
                        .filter(other -> !coord.equals(other))
                        // Accounts for curning
                        .filter(other -> calculateDistance(coord, other) <= 2)
                        .count() >= 1)
                .count();
    }

    private int calculateDistance(final Coordinates a, final Coordinates b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    private void printStarImage(final Map<Star, Coordinates> starMap,
            final int maxRows, final int minRows,
            final int maxCols, final int minCols) {
        IntStream.range(minRows, maxRows + 1).forEach(row -> {
            IntStream.range(minCols, maxCols + 1).forEach(col -> {
                final Optional<Coordinates> found = starMap.values().stream()
                        .filter(coord -> coord.x() == col && coord.y() == row)
                        .findFirst();
                System.out.print(found.isPresent() ? "#" : ".");
            });
            System.out.println();
        });
    }
}
