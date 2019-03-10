package me.philcali.aoc.day1.year2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public Direction rotate(final String direction) {
        switch (this) {
        case NORTH:
            return direction.equals("L") ? WEST : EAST;
        case EAST:
            return direction.equals("L") ? NORTH : SOUTH;
        case SOUTH:
            return direction.equals("L") ? EAST : WEST;
        case WEST:
        default:
            return direction.equals("L") ? SOUTH : NORTH;
        }
    }

    public List<List<Integer>> move(final int step) {
        final List<List<Integer>> points = new ArrayList<>();
        IntStream.range(1, step + 1).forEach(smallStep -> {
            switch (this) {
            case NORTH:
                points.add(Arrays.asList(0, -1));
                break;
            case SOUTH:
                points.add(Arrays.asList(0, 1));
                break;
            case EAST:
                points.add(Arrays.asList(1, 0));
                break;
            case WEST:
            default:
                points.add(Arrays.asList(-1, 0));
                break;
            }
        });
        return points;
    }
}
