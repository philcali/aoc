package me.philcali.aoc.day4.year2015;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Description;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(4) @Problem(1) @Year(2015)
@Description("The Ideal Stocking Stuffer")
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent {
    @Override
    public void run() {
        final String input = "yzbqklnj";
        System.out.println("First five zero: " + new ComputeHash().apply(input, 5));
    }
}
