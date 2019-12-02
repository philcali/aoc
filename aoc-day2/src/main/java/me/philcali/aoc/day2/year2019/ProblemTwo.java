package me.philcali.aoc.day2.year2019;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Problem(2) @Year(2019) @Day(2)
@AutoService(DailyEvent.class)
public class ProblemTwo implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final int result = 19690720;
        final Program program = Program.fromInput(readLines());
        System.out.println("Found " + answer(program, result));
    }

    private int answer(final Program program, final int result) {
        for (int noun = 0; noun <= 99; noun++) {
            for (int verb = 0; verb <= 99; verb++) {
                if (program.output(noun, verb) == result) {
                    return 100 * noun + verb;
                }
            }
        }
        return 0;
    }
}
