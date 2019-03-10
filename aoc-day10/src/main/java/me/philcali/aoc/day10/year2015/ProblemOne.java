package me.philcali.aoc.day10.year2015;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Description;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(10) @Problem(1) @Year(2015)
@Description("Elves Look, Elves Say")
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent {
    @Override
    public void run() {
        String input = "3113322113";
        System.out.println("After 40 times: " + new Loop(new LookAndSay()).apply(input, 40).length());
    }
}
