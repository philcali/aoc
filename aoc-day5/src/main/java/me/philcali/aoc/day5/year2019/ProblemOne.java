package me.philcali.aoc.day5.year2019;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;
import me.philcali.aoc.common.intcode.Program;
import me.philcali.aoc.common.intcode.ProgramHaltsException;

@Problem(1) @Year(2019) @Day(5)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final Program program = Program.fromLines(readLines());
        try {
            while (true) {
                program.run();
            }
        } catch (ProgramHaltsException e) {
            System.out.println("Finished");
        }
    }
}
