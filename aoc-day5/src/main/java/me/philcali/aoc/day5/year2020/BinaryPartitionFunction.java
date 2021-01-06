package me.philcali.aoc.day5.year2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BinaryPartitionFunction implements Function<List<String>, List<Integer>> {
    @Override
    public List<Integer> apply(final List<String> lines) {
        final List<Integer> setIds = new ArrayList<>();
        for (final String line : lines) {
            int[] rowRange = new int[] { 0, 127 };
            int[] colRange = new int[] { 0, 7 };
            char[] chars = line.toCharArray();
            int row = 0, col = 0;
            for (int index = 0; index < chars.length; index++) {
                switch (chars[index]) {
                    case 'F':
                    case 'B':
                        int rowI = chars[index] == 'F' ? 1 : 0;
                        int rowHalf = chars[index] == 'B' ? 1 : 0;
                        int updatedRow = ((rowRange[1] - rowRange[0]) / 2) + rowHalf;
                        if (chars[index] == 'F') {
                            updatedRow = rowRange[1] - (updatedRow + 1);
                        } else {
                            updatedRow = rowRange[0] + updatedRow;
                        }
                        rowRange[rowI] = updatedRow;
                        if (index == 6) {
                            row = rowRange[rowHalf];
                        }
                        break;
                    case 'R':
                    case 'L':
                        int colI = chars[index] == 'L' ? 1 : 0;
                        int colHalf = chars[index] == 'R' ? 1 : 0;
                        int updatedCol = ((colRange[1] - colRange[0]) / 2) + colHalf;
                        if (chars[index] == 'L') {
                            updatedCol = colRange[1] - (updatedCol + 1);
                        } else {
                            updatedCol = colRange[0] + updatedCol;
                        }
                        colRange[colI] = updatedCol;
                        if (index == 9) {
                            col = colRange[colHalf];
                        }
                        break;
                }
            }
            int seatId = row * 8 + col;
            setIds.add(seatId);
        }
        Collections.sort(setIds);
        return setIds;
    }
}
