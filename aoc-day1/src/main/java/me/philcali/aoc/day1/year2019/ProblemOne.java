package me.philcali.aoc.day1.year2019;

import java.util.function.Function;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Year(2019) @Day(1) @Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final Function<Integer, Integer> module = input -> (input / 3) - 2;
        System.out.println("Sum is " + readLines().stream()
                .map(line -> Integer.parseInt(line))
                .map(module)
                .reduce(0, (left, right) -> left + right));
    }
}
