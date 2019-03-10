package me.philcali.aoc.day8.year2018;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Description;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(8) @Problem(2) @Year(2018)
@Description("Memory Maneuver: Specific Metadata Sums")
@AutoService(DailyEvent.class)
public class ProblemTwo implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        try (final Scanner scanner = new Scanner(streamInput(), StandardCharsets.UTF_8.name())) {
            System.out.println("Root Node Sum: " + sum(Node.fromInput(scanner)));
        }
    }

    private int sum(final Node node) {
        if (node.children().isEmpty()) {
            return node.sum();
        } else {
            int sum = 0;
            for (final int metadataIndex : node.metadata()) {
                try {
                    sum += sum(node.children().get(metadataIndex - 1));
                } catch (IndexOutOfBoundsException iobe) {
                    sum += 0;
                }
            }
            return sum;
        }
    }
}
