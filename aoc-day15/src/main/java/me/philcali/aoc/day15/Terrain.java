package me.philcali.aoc.day15;

import java.util.Arrays;
import java.util.Optional;

import me.philcali.aoc.common.geometry.Point;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface Terrain {
    @NonNull
    Point location();
    @NonNull
    Type type();

    enum Type {
        WALL('#'),
        OPEN_SPACE('.');

        private final char symbol;

        Type(final char symbol) {
            this.symbol = symbol;
        }

        public char symbol() {
            return symbol;
        }

        public boolean isObstruction() {
            return this == WALL;
        }

        public static Optional<Type> fromSymbol(final char symbol) {
            return Arrays.stream(Type.values())
                    .filter(type -> type.symbol() == symbol)
                    .findFirst();
        }
    }
}
