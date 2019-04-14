package me.philcali.aoc.day6.year2018;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;
import me.philcali.aoc.common.geometry.Point;
import me.philcali.aoc.common.geometry.Rectangular;

@Day(6) @Problem(1) @Year(2018)
@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent, AnnotatedDailyEvent {
    final Function<List<String>, Set<Point>> parser;

    public ProblemOne(final Function<List<String>, Set<Point>> parser) {
        this.parser = parser;
    }

    public ProblemOne() {
        this(new LinesToPointsFunction());
    }

    @Override
    public void run() {
        final Set<Point> points = parser.apply(readLines());
        final Rectangular boundary = Rectangular.boundary(points);
        final Map<Point, Integer> areas = new HashMap<>();
        points.stream().forEach(point -> {
            areas.put(point, 1);
            final Set<Point> otherPoints = new HashSet<>(points);
            otherPoints.remove(point);
            int distance = 0;
            List<Point> radialPoints = Collections.emptyList();
            do {
                radialPoints = point.radial(++distance);
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
