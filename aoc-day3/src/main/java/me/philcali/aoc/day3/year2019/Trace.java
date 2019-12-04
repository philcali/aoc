package me.philcali.aoc.day3.year2019;

import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.philcali.aoc.common.geometry.Point;
import me.philcali.aoc.common.geometry.PointData;

public interface Trace {
    Pattern DIRECTION_REGEX = Pattern.compile("([L|U|R|D])(\\d+)");

    static void draw(final Point startingPoint, final String line, final BiConsumer<Point, Integer> handlePoint) {
        Point tracePointer = new PointData(startingPoint.x(), startingPoint.y());
        int step = 0;
        for (final String instruction : line.split(",")) {
            final Matcher matcher = DIRECTION_REGEX.matcher(instruction);
            if (matcher.matches()) {
                int distance = Integer.parseInt(matcher.group(2));
                for (int move = 0; move < distance; move++) {
                    switch (matcher.group(1)) {
                    case "L":
                        tracePointer = new PointData(tracePointer.x() - 1, tracePointer.y());
                        break;
                    case "R":
                        tracePointer = new PointData(tracePointer.x() + 1, tracePointer.y());
                        break;
                    case "U":
                        tracePointer = new PointData(tracePointer.x(), tracePointer.y() - 1);
                        break;
                    case "D":
                        tracePointer = new PointData(tracePointer.x(), tracePointer.y() + 1);
                        break;
                    }
                    handlePoint.accept(tracePointer, ++step);
                }
            }
        }
    }
}
