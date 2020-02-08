package me.philcali.aoc.day10.year2019;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;
import me.philcali.aoc.common.geometry.Point;
import me.philcali.aoc.common.geometry.PointData;

@Year(2019) @Day(10) @Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final Set<Point> asteroids = new HashSet<>();
        final List<String> starField = readLines();
        final char[][] map = new char[starField.size()][starField.size()];
        for (int y = 0; y < starField.size(); y++) {
            final String line = starField.get(y);
            for (int x = 0; x < starField.size(); x++) {
                final char data = line.charAt(x);
                if (data == '#') {
                    asteroids.add(new PointData(x, y));
                }
                map[x][y] = data;
            }
        }
        for (final Point potentialBase : asteroids) {
            System.out.println(potentialBase);
            final Set<Point> inLineOfSight = new HashSet<>();
            final Set<Point> blackListed = new HashSet<>();
            final List<Point> closest = new ArrayList<>(asteroids);
            closest.remove(potentialBase);
            Collections.sort(closest, (left, right) -> {
                return Integer.compare(left.distance(potentialBase), right.distance(potentialBase));
            });
            System.out.println(closest);
            for (final Point asteroid : closest) {
                int xOffset = asteroid.x() - potentialBase.x();
                int yOffset = asteroid.y() - potentialBase.y();
                if (!blackListed.contains(asteroid) && inLineOfSight.add(asteroid)) {
                    int xStep = xOffset;
                    int yStep = yOffset;
                    while (xStep >= 0 && xStep <= starField.size() && yStep >= 0 && yStep <= starField.size()) {
                        xStep += xOffset;
                        yStep += yOffset;
                        blackListed.add(new PointData(xStep, yStep));
                    }
                    System.out.println(blackListed);
                }
            }
            break;
        }
    }
}
