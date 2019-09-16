package me.philcali.aoc.day13;

import java.util.Optional;

import me.philcali.aoc.common.geometry.Point;
import me.philcali.aoc.common.geometry.PointData;
import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.NonNull;
import me.philcali.zero.lombok.annotation.ToString;

@Builder
@ToString
public interface Cart extends Comparable<Cart> {
    @NonNull
    Point initial();
    @NonNull
    Point coord();
    @NonNull
    Direction direction();
    @NonNull
    Option turnOption();

    default Cart move(final Track layout) {
        Option nextOption = turnOption();
        Direction direction = direction();
        switch (layout) {
        case INTERSECTION:
            direction = direction.turn(nextOption);
            nextOption = nextOption.next();
            break;
        case LEFT_CORNER:
            direction = direction.turn(direction == Direction.LEFT || direction == Direction.RIGHT ? Option.LEFT : Option.RIGHT);
            break;
        case RIGHT_CORNER:
            direction = direction.turn(direction == Direction.LEFT || direction == Direction.RIGHT ? Option.RIGHT : Option.LEFT);
            break;
        default:
        }
        return CartData.builder()
                .initial(initial())
                .coord(direction.tick(coord()))
                .direction(direction)
                .turnOption(nextOption)
                .build();
    }

    @Override
    default int compareTo(final Cart other) {
        final int y = Integer.compare(coord().y(), other.coord().y());
        if (y == 0) {
            return Integer.compare(coord().x(), other.coord().x());
        } else {
            return y;
        }
    }

    enum Option {
        LEFT,
        STRAIGHT,
        RIGHT;

        public Option next() {
            switch(this) {
            case LEFT:
                return STRAIGHT;
            case STRAIGHT:
                return RIGHT;
            case RIGHT:
            default:
                return LEFT;
            }
        }
    }

    enum Direction {
        UP('^'),
        DOWN('v'),
        LEFT('<'),
        RIGHT('>');

        private char layout;

        Direction(final char layout) {
            this.layout = layout;
        }

        public Direction turn(final Option option) {
            switch (option) {
            case LEFT:
                switch(this) {
                case UP:
                    return LEFT;
                case DOWN:
                    return RIGHT;
                case LEFT:
                    return DOWN;
                default:
                    return UP;
                }
            case RIGHT:
                switch (this) {
                case UP:
                    return RIGHT;
                case DOWN:
                    return LEFT;
                case LEFT:
                    return UP;
                default:
                    return DOWN;
                }
            default:
                return this;
            }
        }

        public Point tick(final Point location) {
            int x = location.x();
            int y = location.y();
            switch (this) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
            }
            return new PointData(x, y);
        }

        public char layout() {
            return layout;
        }

        public static Optional<Direction> fromLayout(final char layout) {
            for (final Direction direction : values()) {
                if (direction.layout() == layout) {
                    return Optional.of(direction);
                }
            }
            return Optional.empty();
        }
    }
}
