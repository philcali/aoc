package me.philcali.aoc.day3;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClaimsToMatrixFunction implements Function<List<String>, Cell[][]> {
    private static final int LENGTH = 1000;
    private static final Pattern FABRIC_PATTERN = Pattern.compile("#(\\d+)\\s*@\\s*(\\d+),(\\d+):\\s*(\\d+)x(\\d+)");

    @Override
    public Cell[][] apply(final List<String> lines) {
        final Cell[][] matrix = new Cell[LENGTH][LENGTH];
        for (final String fabric : lines) {
            final Matcher matcher = FABRIC_PATTERN.matcher(fabric);
            if (matcher.matches()) {
                final ClaimData claim = new ClaimData(Integer.parseInt(matcher.group(1)));
                final int x = Integer.parseInt(matcher.group(2));
                final int y = Integer.parseInt(matcher.group(3));
                final int width = Integer.parseInt(matcher.group(4));
                final int height = Integer.parseInt(matcher.group(5));
                for (int left = 0; left < width; left++) {
                    for (int down = 0; down < height; down++) {
                        final Cell existing = Optional.ofNullable(matrix[x + left][y + down]).orElseGet(Cell::new);
                        existing.addClaim(claim);
                        matrix[x + left][y + down] = existing;
                    }
                }
            }
        }
        return matrix;
    }
}
