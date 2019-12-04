package me.philcali.aoc.day3.year2019;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
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

@Year(2019) @Day(3) @Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent, AnnotatedDailyEvent {

    @Override
    public void run() {
        final Point center = new PointData(0, 0);
        final List<String> lines = readLines();
        final Set<Point> initialTrace = new HashSet<>();
        final Queue<Point> closestIntersections = new PriorityQueue<>((left, right) -> {
            return Integer.compare(left.distance(center), right.distance(center));
        });
        Trace.draw(center, lines.get(0), (point, step) -> initialTrace.add(point));
        Trace.draw(center, lines.get(1), (tracePoint, step) -> {
            if (initialTrace.contains(tracePoint)) {
                closestIntersections.add(tracePoint);
            }
        });
        System.out.println("Closest intersection distance " + closestIntersections.poll().distance(center));
    }
}
