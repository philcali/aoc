package me.philcali.aoc.day8.year2019;

import java.util.List;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Year(2019) @Day(8) @Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final List<String> layers = Layers.fromInput(readLines());
        int zeroCount = Integer.MAX_VALUE;
        String matchingLine = null;
        for (final String layer : layers) {
            final int zeros = numberOf('0', layer);
            if (zeros < zeroCount) {
                zeroCount = zeros;
                matchingLine = layer;
            }
        }
        System.out.println("Multiply: " + (numberOf('1', matchingLine) * numberOf('2', matchingLine)));
    }

    private int numberOf(final char number, final String line) {
        int inLineCount = 0;
        for (char letter : line.toCharArray()) {
            if (letter == number) {
                inLineCount++;
            }
        }
        return inLineCount;
    }
}
