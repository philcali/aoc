package me.philcali.aoc.day3.year2019;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;
import me.philcali.aoc.common.geometry.Point;
import me.philcali.aoc.common.geometry.PointData;

@Problem(2) @Day(3) @Year(2019)
@AutoService(DailyEvent.class)
public class ProblemTwo implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final Point center = new PointData(0, 0);
        final List<String> lines = readLines();
        final Map<Point, Integer> initialTrace = new HashMap<>();
        final AtomicInteger minimalSteps = new AtomicInteger(Integer.MAX_VALUE);
        Trace.draw(center, lines.get(0), initialTrace::put);
        Trace.draw(center, lines.get(1), (tracePoint, step) -> {
            if (initialTrace.containsKey(tracePoint)) {
                final int totalSteps = step + initialTrace.get(tracePoint);
                minimalSteps.set(Math.min(totalSteps, minimalSteps.get()));
            }
        });
        System.out.println("Minimal steps intersection " + minimalSteps.get());
    }
}
