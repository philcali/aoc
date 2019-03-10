package me.philcali.aoc.day8.year2018;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.IntStream;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Description;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(8) @Problem(1) @Year(2018)
@Description("Memory Maneuver")
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {

    @Override
    public void run() {
        try(final Scanner scanner = new Scanner(streamInput(), StandardCharsets.UTF_8.name())) {
            System.out.println("Total Sum: " + parseInput(scanner).sum());
        }
    }

    private Node parseInput(final Scanner scanner) {
        final int childrenCount = scanner.nextInt();
        final int metadataCount = scanner.nextInt();
        final NodeData.Builder builder = NodeData.builder().addChildren().addMetadata();
        IntStream.range(0, childrenCount).forEach(time -> {
            builder.addChildren(parseInput(scanner));
        });
        IntStream.range(0, metadataCount).forEach(time -> {
            builder.addMetadata(scanner.nextInt());
        });
        return builder.build();
    }
}
