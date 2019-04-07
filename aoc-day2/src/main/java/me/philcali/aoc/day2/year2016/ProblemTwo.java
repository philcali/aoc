package me.philcali.aoc.day2.year2016;

import java.util.Stack;
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

@Day(2) @Problem(2) @Year(2016)
@Description("Bathroom Security: Part Two")
@AutoService(DailyEvent.class)
public class ProblemTwo implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final Cell[][] keypad = new Cell[][] {
            new Cell[] { Cell.empty(), Cell.empty(), Cell.valued('1'), Cell.empty(), Cell.empty() },
            new Cell[] { Cell.empty(), Cell.valued('2'), Cell.valued('3'), Cell.valued('4'), Cell.empty() },
            new Cell[] { Cell.valued('5'), Cell.valued('6'), Cell.valued('7'), Cell.valued('8'), Cell.valued('9') },
            new Cell[] { Cell.empty(), Cell.valued('A'), Cell.valued('B'), Cell.valued('C'), Cell.empty() },
            new Cell[] { Cell.empty(), Cell.empty(), Cell.valued('D'), Cell.empty(), Cell.empty() },
        };
        final Stack<Direction> history = new Stack<>();
        final Cursor cursor = new CursorData(new AtomicInteger(0), new AtomicInteger(2));
        for (final String line : readLines()) {
            for (final char letter : line.toCharArray()) {
                final Direction direction = Direction.valueOf(Character.toString(letter));
                history.push(direction);
                cursor.move(direction, keypad.length);
                if (keypad[cursor.y().get()][cursor.x().get()].isOpaque()) {
                    cursor.move(history.pop().reverse(), keypad.length);
                }
            }
            System.out.print(keypad[cursor.y().get()][cursor.x().get()].value());
        }
        System.out.println();
    }
}
