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

@Day(8) @Problem(1) @Year(2018)
@Description("Memory Maneuver")
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        try(final Scanner scanner = new Scanner(streamInput(), StandardCharsets.UTF_8.name())) {
            System.out.println("Total tree sum: " + sum(Node.fromInput(scanner)));
        }
    }

    private int sum(final Node node) {
        return node.children().stream().reduce(node.sum(),
                (left, right) -> left + sum(right),
                (left, right) -> left);
    }
}
