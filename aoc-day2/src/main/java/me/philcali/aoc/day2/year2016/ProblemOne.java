package me.philcali.aoc.day2.year2016;

import java.util.concurrent.atomic.AtomicInteger;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Description;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;
import me.philcali.aoc.day2.year2016.Cursor.Direction;

@Day(2) @Problem(1) @Year(2016)
@Description("Bathroom Security")
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final int[][] matrix = new int[][] {
            new int[] { 1, 2, 3 },
            new int[] { 4, 5, 6 },
            new int[] { 7, 8, 9 }
        };
        final Cursor cursor = new CursorData(new AtomicInteger(1), new AtomicInteger(1));
        for (final String line : readLines()) {
            for (final char letter : line.toCharArray()) {
                final Direction direction = Direction.valueOf(Character.toString(letter));
                cursor.move(direction, matrix.length);
            }
            System.out.print(matrix[cursor.y().get()][cursor.x().get()]);
        }
        System.out.println();
    }
}
