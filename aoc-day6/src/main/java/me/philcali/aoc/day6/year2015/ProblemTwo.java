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

@AutoService(DailyEvent.class)
@Day(6) @Problem(2) @Year(2015)
@Description("Probably a Fire Hazard: Part Two")
public class ProblemTwo implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final int[][] grid = Instruction.grid();
        for (final String line : readLines()) {
            final Instruction instruction = Instruction.fromString(line);
            instruction.traverse(point -> {
                switch (instruction.action()) {
                case ON:
                    grid[point.y()][point.x()]++;
                    break;
                case OFF:
                    grid[point.y()][point.x()] = Math.max(0, --grid[point.y()][point.x()]);
                    break;
                default:
                    grid[point.y()][point.x()] += 2;
                }
            });
        }
        System.out.println("Total number of lights left on: " + Arrays.stream(grid)
        .flatMapToInt(x -> Arrays.stream(x)).filter(n -> n >= 1)
        .sum());
    }
}
