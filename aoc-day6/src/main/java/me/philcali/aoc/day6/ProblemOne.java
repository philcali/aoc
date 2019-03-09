package me.philcali.aoc.day6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;

@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent {
    private static final Pattern COORDINATES = Pattern.compile("^(\\d+),\\s*(\\d+)$");

    @Override
    public int day() {
        return 6;
    }

    @Override
    public int problem() {
        return 1;
    }

    @Override
    public int year() {
        return 2018;
    }

    private Rectangular createBoundary(final Set<Point> points) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (final Point point : points) {
            if (point.x() < minX) {
                minX = point.x();
            }
            if (point.x() > maxX) {
                maxX = point.x();
            }
            if (point.y() < minY) {
                minY = point.y();
            }
            if (point.y() > maxY) {
                maxY = point.y();
            }
        }
        return RectangularData.builder()
                .topLeft(new PointData(minX, minY))
                .topRight(new PointData(maxX, minY))
                .bottomLeft(new PointData(minX, maxY))
                .bottomRight(new PointData(maxX, maxY))
                .build();
    }

    private List<Point> radial(final int distance, final Point center) {
        final List<Point> points = new ArrayList<>();
        IntStream.range(0, distance).forEach(between -> {
            points.addAll(Arrays.asList(
                    new PointData(center.x() + distance - between, center.y() + between),
                    new PointData(center.x() - distance + between, center.y() - between),
                    new PointData(center.x() + between, center.y() - distance + between),
                    new PointData(center.x() - between, center.y() + distance - between)));
        });
        return points;
    }

    @Override
    public void run() {
        final Set<Point> points = new HashSet<>();
        for (final String line : readLines()) {
            final Matcher matcher = COORDINATES.matcher(line);
            if (matcher.matches()) {
                points.add(new PointData(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2))));
            }
        }
        final Rectangular boundary = createBoundary(points);
        System.out.println(boundary);
        final Map<Point, Integer> areas = new HashMap<>();
        points.stream().forEach(point -> {
            areas.put(point, 1);
            final Set<Point> otherPoints = new HashSet<>(points);
            otherPoints.remove(point);
            int distance = 0;
            List<Point> radialPoints = Collections.emptyList();
            do {
                radialPoints = radial(++distance, point);
                Iterator<Point> iter = radialPoints.listIterator();
                while (iter.hasNext()) {
                    final Point testPoint = iter.next();
                    for (final Point otherPoint : otherPoints) {
                        if (otherPoint.distance(testPoint) <= distance) {
                            iter.remove();
                            break;
                        }
                    }
                }
                final int radialLeftOver = radialPoints.size();
                areas.computeIfPresent(point, (p, count) -> radialLeftOver + count);
                for (final Point testPoint : radialPoints) {
                    if (!boundary.contains(testPoint)) {
                        areas.remove(point);
                        break;
                    }
                }
                // Area is contained or area is indefinite
            } while (!radialPoints.isEmpty() && areas.containsKey(point));
        });
        int largestArea = 0;
        for (final Map.Entry<Point, Integer> entry : areas.entrySet()) {
            if (largestArea < entry.getValue()) {
                largestArea = entry.getValue();
            }
        }
        System.out.println("Largest Area: " + largestArea);
    }
}
