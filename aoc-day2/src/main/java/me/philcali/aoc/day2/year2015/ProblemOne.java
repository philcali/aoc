package me.philcali.aoc.day2.year2015;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(2) @Problem(1) @Year(2015)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        int totalSquares = readLines().stream().map(Prism::fromLine).reduce(0,
                (left, right) -> left + right.volume() + right.sides().get(0).area(),
                (left, right) -> left);
        System.out.println("Total square feet required: " + totalSquares);
    }
}
