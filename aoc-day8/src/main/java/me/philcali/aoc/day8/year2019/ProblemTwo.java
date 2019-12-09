package me.philcali.aoc.day8.year2019;

import java.util.List;
import java.util.stream.IntStream;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Year(2019) @Problem(2) @Day(8)
@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent, AnnotatedDailyEvent {
    @Override
    public void run() {
        final List<String> layers = Layers.fromInput(readLines());
        final StringBuilder finalImage = new StringBuilder();
        IntStream.range(0, (Layers.WIDTH * Layers.HEIGHT)).forEach(pixel -> {
            for (final String layer : layers) {
                final char value = layer.charAt(pixel);
                if (value == '1' || value == '0') {
                    finalImage.append(value == '0' ? ' ' : '.');
                    break;
                }
            }
        });

        IntStream.range(0, Layers.HEIGHT).forEach(row -> {
            System.out.println(finalImage.substring(row * Layers.WIDTH, (row * Layers.WIDTH) + Layers.WIDTH));
        });
    }
}
