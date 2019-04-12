package me.philcali.aoc.day6.year2015;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data @Builder
public interface Instruction {
    Pattern INSTRUCTION = Pattern.compile("^(toggle|turn on|turn off)\\s*([^\\s]+)\\s*through\\s*(.+)");

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
    Coordinates starting();

    @NonNull
    Coordinates ending();

    static Instruction fromString(final String line) {
        final Matcher matcher = INSTRUCTION.matcher(line);
        if (matcher.matches()) {
            return InstructionData.builder()
                    .action(Action.fromLabel(matcher.group(1)))
                    .starting(Coordinates.fromString(matcher.group(2)))
                    .ending(Coordinates.fromString(matcher.group(3)))
                    .build();
        }
        throw new IllegalArgumentException("Line " + line + " is not valid instructions.");
    }

    default void traverse(final Consumer<Coordinates> thunk) {
        IntStream.range(starting().y(), ending().y() + 1).forEach(y -> {
            IntStream.range(starting().x(), ending().x() + 1).forEach(x -> {
                thunk.accept(new CoordinatesData(x, y));
            });
        });
    }
}
