package me.philcali.aoc.day6.year2015;

import java.util.Arrays;

import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface Coordinates {
    int SIZE = 1000;

    @NonNull
    int x();
    @NonNull
    int y();

    static Coordinates fromString(final String input) {
        final String[] parts = input.split("\\s*,\\s*");
        return new CoordinatesData(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
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
}
