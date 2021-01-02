package me.philcali.aoc.day1.year2020;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Data @Builder
public interface Calculator {

    List<String> input();

    int iterations();

    default long fillValues(int iterIndex, Map<Integer, Long> indexToValue, List<Integer> ints) {
        if (iterIndex == iterations()) {
            Optional<Long> sum = indexToValue.values().stream().reduce((left, right) -> left + right);
            if (sum.filter(value -> value == 2020).isPresent()) {
                return indexToValue.values().stream().reduce((left, right) -> left * right).get();
            }
            return -1;
        }
        for (int index = 0; index < ints.size(); index++) {
            if (indexToValue.containsKey(index)) {
                continue;
            }
            indexToValue.put(index, (long) ints.get(index));
            long result = fillValues(iterIndex + 1, indexToValue, ints);
            if (result != -1) {
                return result;
            }
            indexToValue.remove(index);
        }
        return -1;
    }

    default long evaluate() {
        final List<Integer> ints = input().stream().map(Integer::parseInt).collect(Collectors.toList());
        final Map<Integer, Long> indexToValue = new HashMap<>();
        return fillValues(0, indexToValue, ints);
    }
}
