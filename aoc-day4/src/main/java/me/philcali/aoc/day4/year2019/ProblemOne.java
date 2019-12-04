package me.philcali.aoc.day4.year2019;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Year(2019) @Day(4) @Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent {
    @Override
    public void run() {
        final int start = 357253;
        final int ending = 892942;
        final List<Integer> numbers = new ArrayList<>();
        final Predicate<String> rules = new LengthRule()
                .and(new NeverDecreasingRule())
                .and(new ForceAdjacencyRule(number -> number >= 2));
        for (int index = start; index < ending; index++) {
            if (rules.test(Integer.toString(index))) {
                numbers.add(index);
            }
        }
        System.out.println(numbers);
        System.out.println("There are this many passwords " + numbers.size());
    }
}
