package me.philcali.aoc.day10.year2015;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Description;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(10) @Problem(2) @Year(2015)
@Description("Elves Look, Elves Say: 50 times")
@AutoService(DailyEvent.class)
public class ProblemTwo implements AnnotatedDailyEvent {
    @Override
    public void run() {
        String input = "3113322113";
        System.out.println("After 50 times: " + new Loop(new LookAndSay()).apply(input, 50).length());
    }
}
