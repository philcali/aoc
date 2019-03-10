package me.philcali.aoc.day1.year2016;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Description;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(1) @Problem(2) @Year(2016)
@Description("No Time for a Taxicab: First Repeated Position")
@AutoService(DailyEvent.class)
public class ProblemTwo implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final Iterator<List<Integer>> steps = new StepGenerator(readLines());
        final List<Integer> startingPoint = Arrays.asList(0, 0);
        final Set<List<Integer>> uniquePositions = new HashSet<>();
        List<Integer> endingPoint = null;
        while (steps.hasNext()) {
            endingPoint = steps.next();
            if (!uniquePositions.add(endingPoint)) {
                break;
            }
        }
        System.out.println("Total Steps: " +
                (Math.abs(startingPoint.get(0) + endingPoint.get(0)) + Math.abs(startingPoint.get(1) + endingPoint.get(1))));
    }
}
