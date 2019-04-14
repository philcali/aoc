package me.philcali.aoc.day6.year2015;

import java.util.Arrays;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Description;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(6) @Problem(1) @Year(2015)
@Description("Probably a fire hazard")
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {

    @Override
    public void run() {
        final int[][] grid = Instruction.grid();
        for (final String line : readLines()) {
            final Instruction instruction = Instruction.fromString(line);
            instruction.traverse(point -> {
                switch (instruction.action()) {
                case ON:
                    grid[point.y()][point.x()] = 1;
                    break;
                case OFF:
                    grid[point.y()][point.x()] = 0;
                    break;
                default:
                    grid[point.y()][point.x()] = grid[point.y()][point.x()] == 0 ? 1 : 0;
                }
            });
        }
        System.out.println("Total number of lights left on: " + Arrays.stream(grid).flatMapToInt(x -> Arrays.stream(x)).filter(n -> n == 1).count());
    }
}
