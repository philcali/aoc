package me.philcali.aoc.day10.year2019;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;
import me.philcali.aoc.common.geometry.Line;
import me.philcali.aoc.common.geometry.LineData;
import me.philcali.aoc.common.geometry.Point;

@Day(10) @Problem(2) @Year(2019)
@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent, AnnotatedDailyEvent {

    public static void main(final String[] args) {
        //System.setProperty("INPUT", "test");
        new ProblemTwo().run();
    }

    @Override
    public void run() {

        final Set<Point> asteroids = Starfield.asteroids(readLines());
        final MonitoringStation station = MonitoringStation.optimal(asteroids);
        double currentAngle = Math.PI;
        System.out.println("Starting at " + station.asteroid() + " " + currentAngle);
        asteroids.remove(station.asteroid());
        final List<Point> closestAsteroids = new ArrayList<>(asteroids);
        Collections.sort(closestAsteroids, (left, right) -> {
            return Integer.compare(left.distance(station.asteroid()), right.distance(station.asteroid()));
        });
        int destructionLimit = 200;
        final List<Point> destroyed = new ArrayList<>();
        while (destroyed.size() < destructionLimit || closestAsteroids.isEmpty()) {
            final SortedMap<Double, Point> visibleRocksInRange = new TreeMap<>((left, right) -> {
                return Double.compare(right, left);
            });
            closestAsteroids.stream().forEach(asteroid -> {
                final Line target = new LineData(station.asteroid(), asteroid);
                visibleRocksInRange.compute(shiftSlope(invertAxis(target.slope())), (slope, rock) -> {
                    if (Objects.isNull(rock)) {
                        return asteroid;
                    } else {
                        return station.asteroid().distance(rock) < station.asteroid().distance(asteroid) ? rock : asteroid;
                    }
                });
            });
            visibleRocksInRange.forEach((slope, asteroid) -> {
                if (closestAsteroids.remove(asteroid)) {
                    destroyed.add(asteroid);
                }
            });
        }
        System.out.println("200th is " + destroyed.get(199));
        System.out.println("Answer is: " + ((destroyed.get(199).x() * 100) + (destroyed.get(199).y())));
    }

    private double invertAxis(final double slope) {
        return slope * -1;
    }

    private double shiftSlope(final double slope) {
        if (slope <= Math.PI / 2) {
            return slope + (Math.PI / 2);
        } else {
            return slope - (Math.PI + (Math.PI / 2));
        }
    }
}
