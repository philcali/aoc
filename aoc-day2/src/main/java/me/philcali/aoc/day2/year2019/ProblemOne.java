package me.philcali.aoc.day2.year2019;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Year(2019) @Day(2) @Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent, AnnotatedDailyEvent {
    @Override
    public void run() {
        System.out.println("Program output " + Program.fromInput(readLines()).output(12, 2));
    }
}
