package me.philcali.aoc.day6.year2015;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import me.philcali.aoc.common.geometry.Point;
import me.philcali.aoc.common.geometry.PointData;
import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data @Builder
public interface Instruction {
    int SIZE = 1000;
    Pattern INSTRUCTION = Pattern.compile("^(toggle|turn on|turn off)\\s*(\\d+),(\\d+)\\s*through\\s*(\\d+),(\\d+)");

    enum Action {
        ON("turn on"), OFF("turn off"), TOGGLE("toggle");

        private String label;

        Action(final String label) {
            this.label = label;
        }

        public String label() {
            return label;
        }

        public static Action fromLabel(final String label) {
            for (final Action action : Action.values()) {
                if (action.label().equals(label)) {
                    return action;
                }
            }
            throw new IllegalArgumentException("Invalid label " + label);
        }
    }

    @NonNull
    Action action();

    @NonNull
    Point starting();

    @NonNull
    Point ending();

    static Instruction fromString(final String line) {
        final Matcher matcher = INSTRUCTION.matcher(line);
        if (matcher.matches()) {
            return InstructionData.builder()
                    .action(Action.fromLabel(matcher.group(1)))
                    .starting(new PointData(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3))))
                    .ending(new PointData(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5))))
                    .build();
        }
        throw new IllegalArgumentException("Line " + line + " is not valid instructions.");
    }

    static int[][] grid() {
        final int[][] grid = new int[SIZE][SIZE];
        for (int y = 0; y < SIZE; y++) {
            final int[] x = new int[SIZE];
            Arrays.fill(x, 0);
            grid[y] = x;
        }
        return grid;
    }

    default void traverse(final Consumer<Point> thunk) {
        IntStream.range(starting().y(), ending().y() + 1).forEach(y -> {
            IntStream.range(starting().x(), ending().x() + 1).forEach(x -> {
                thunk.accept(new PointData(x, y));
            });
        });
    }
}
