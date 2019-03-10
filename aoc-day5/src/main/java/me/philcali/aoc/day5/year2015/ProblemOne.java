package me.philcali.aoc.day5.year2015;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Description;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(5) @Problem(1) @Year(2015)
@Description("Doesn't He Have Intern-Elves For This?")
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final List<String> nice = new ArrayList<>();
        final Predicate<String> niceOne = new ThreeVowels().and(new DoubleOccurance()).and(new BadActors().negate());
        for (final String line : readLines()) {
            if (niceOne.test(line)) {
                nice.add(line);
            }
        }
        System.out.println("Found some nice lines: " + nice.size());
    }
}
