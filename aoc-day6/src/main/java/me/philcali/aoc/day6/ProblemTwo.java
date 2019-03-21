package me.philcali.aoc.day6;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(6) @Problem(2) @Year(2018)
@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent, AnnotatedDailyEvent {
    private static final int MAX_DISTANCE = 10000;
    private final Function<List<String>, Set<Point>> parser;

    public ProblemTwo(final Function<List<String>, Set<Point>> parser) {
        this.parser = parser;
    }

    public ProblemTwo() {
        this(new LinesToPointsFunction());
    }

    @Override
    public void run() {
        final Set<Point> points = parser.apply(readLines());
        final Rectangular boundary = Rectangular.boundary(points);
        int regionSize = 1;
        int distance = 0;
        final Point center = boundary.center();
        while (true) {
            if (distance % 100 == 0) {
                System.out.println("Currently expanded at: " + distance);
            }
            final List<Point> radialPoints = center.radial(++distance);
            final Iterator<Point> pointsIter = radialPoints.iterator();
            while (pointsIter.hasNext()) {
                final Point point = pointsIter.next();
                final long pointDistance = points.stream().reduce(0,
                        (left, right) -> left + right.distance(point),
                        (left, right) -> left);
                if (pointDistance >= MAX_DISTANCE) {
                    pointsIter.remove();
                }
            }
            regionSize += radialPoints.size();
            if (radialPoints.isEmpty()) {
                break;
            }
        }
        System.out.println("Total region size: " + regionSize);
    }
}
