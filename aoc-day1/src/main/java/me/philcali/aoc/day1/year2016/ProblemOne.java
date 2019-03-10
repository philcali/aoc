package me.philcali.aoc.day1.year2016;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Description;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(1) @Problem(1) @Year(2016)
@Description("No Time for a Taxicab")
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {

    @Override
    public void run() {
        final List<Integer> startingPoint = Arrays.asList(0, 0);
        final Iterator<List<Integer>> steps = new StepGenerator(readLines());
        List<Integer> endingPoint = null;
        while (steps.hasNext()) {
            endingPoint = steps.next();
        }
        System.out.println("Total Steps: " +
                (Math.abs(startingPoint.get(0) + endingPoint.get(0)) + Math.abs(startingPoint.get(1) + endingPoint.get(1))));
    }
}
