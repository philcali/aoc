package me.philcali.aoc.day1.year2019;

import java.util.function.Function;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Year(2019) @Problem(2) @Day(1)
@AutoService(DailyEvent.class)
public class ProblemTwo implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final Function<Integer, Integer> module = input -> (input / 3) - 2;
        long totalSum = 0;
        for (final String line : readLines()) {
            int mass = module.apply(Integer.parseInt(line));
            int individualSum = 0;
            while (mass > 0) {
                individualSum += mass;
                mass = module.apply(mass);
            }
            totalSum += individualSum;
        }
        System.out.println("Total sum is " + totalSum);
    }
}
