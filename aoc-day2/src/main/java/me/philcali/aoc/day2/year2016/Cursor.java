package me.philcali.aoc.day2.year2016;

import java.util.concurrent.atomic.AtomicInteger;

import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface Cursor {
    enum Direction {
        U, L, R, D;

        public Direction reverse() {
            switch (this) {
            case U:
                return D;
            case D:
                return U;
            case L:
                return R;
            case R:
                return L;
            }
            throw new IllegalArgumentException("Direction " + name() + " cannot be reversed.");
        }
    }

    @NonNull
    AtomicInteger x();

    @NonNull
    AtomicInteger y();

    default void move(final Direction direction, final int boardSize) {
        switch (direction) {
        case U:
            if (y().decrementAndGet() < 0) {
                y().set(0);
            }
            break;
        case D:
            if (y().incrementAndGet() >= boardSize) {
                y().set(boardSize - 1);
            }
            break;
        case L:
            if (x().decrementAndGet() < 0) {
                x().set(0);
            }
            break;
        case R:
            if (x().incrementAndGet() >= boardSize) {
                x().set(boardSize - 1);
            }
            break;
        }
    }
}
