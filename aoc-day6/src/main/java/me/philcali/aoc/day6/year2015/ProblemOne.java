package me.philcali.aoc.day6.year2015;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

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
    private static final int SIZE = 1000;
    private static final Pattern INSTRUCTION = Pattern.compile("^(toggle|turn on|turn off)\\s*([^\\s]+)\\s*through\\s*(.+)");

    @Override
    public void run() {
        final int[][] grid = new int[SIZE][SIZE];
        for (int y = 0; y < SIZE; y++) {
            final int[] x = new int[SIZE];
            Arrays.fill(x, 0);
            grid[y] = x;
        }
        for (final String line : readLines()) {
            final Matcher matcher = INSTRUCTION.matcher(line);
            if (matcher.matches()) {
                final String action = matcher.group(1);
                final Coordinates starting = Coordinates.fromString(matcher.group(2));
                final Coordinates ending = Coordinates.fromString(matcher.group(3));
                IntStream.range(starting.y(), ending.y() + 1).forEach(y -> {
                    IntStream.range(starting.x(), ending.x() + 1).forEach(x -> {
                        switch (action) {
                        case "turn on":
                            grid[y][x] = 1;
                            break;
                        case "turn off":
                            grid[y][x] = 0;
                            break;
                        default:
                            grid[y][x] = grid[y][x] == 0 ? 1 : 0;
                        }
                    });
                });
            }
        }
        System.out.println("Total number of lights left on: " + Arrays.stream(grid).flatMapToInt(x -> Arrays.stream(x)).filter(n -> n == 1).count());
    }
}
