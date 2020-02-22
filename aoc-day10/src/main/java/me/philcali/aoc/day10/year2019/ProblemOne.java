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
import me.philcali.aoc.common.geometry.Line;
import me.philcali.aoc.common.geometry.LineData;
import me.philcali.aoc.common.geometry.Point;
import me.philcali.aoc.common.geometry.PointData;

@Year(2019) @Day(10) @Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {

    public static void main(final String[] args) {
        //System.setProperty("INPUT", "test");
        new ProblemOne().run();
    }

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

        System.out.println("Astroids " + asteroids.size());
        int numVisible = 0;
        Point chosenBase = null;
        for (final Point potentialBase : asteroids) {
            final Set<Double> inLineOfSight = new HashSet<>();
            final List<Point> closest = new ArrayList<>(asteroids);
            closest.remove(potentialBase);
            Collections.sort(closest, (left, right) -> {
                return Integer.compare(left.distance(potentialBase), right.distance(potentialBase));
            });
            for (final Point asteroid : closest) {
                final Line lineOfSight = new LineData(potentialBase, asteroid);
                if (!inLineOfSight.add(lineOfSight.slope())) {
                    System.out.println("Blocked! " + asteroid);
                }
            }
            if (inLineOfSight.size() > numVisible) {
                System.out.println(inLineOfSight);
                chosenBase = potentialBase;
                numVisible = inLineOfSight.size();
            }
            System.out.println("Potential Base: " + potentialBase + " detected " + inLineOfSight.size());
        }
        System.out.println("Chosen base is " + chosenBase + " with " + numVisible + " asteroids");
    }
}
