package me.philcali.aoc.day7.year2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class SettingPermutations {
    private static void generator(List<List<Integer>> permutations, List<Integer> permutation, int[] values, int index) {
        if (index < values.length) {
            for (final int value : values) {
                permutation.set(index, value);
                generator(permutations, permutation, values, index + 1);
            }
        } else {
            // Would otherwise be a combination
            final Set<Integer> uniques = new HashSet<>(permutation);
            if (uniques.size() == permutation.size()) {
                permutations.add(new ArrayList<>(permutation));
            }
        }
    }

    public static List<List<Integer>> create(final int start, final int end) {
        final List<List<Integer>> permutations = new ArrayList<>();
        final int[] possibleValues = IntStream.range(start, end).toArray();
        final List<Integer> permutation = new ArrayList<>();
        Arrays.stream(possibleValues).forEach(e -> permutation.add(0));
        generator(permutations, permutation, possibleValues, 0);
        return permutations;
    }
}
