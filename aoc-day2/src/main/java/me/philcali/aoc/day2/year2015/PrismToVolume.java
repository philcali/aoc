package me.philcali.aoc.day2.year2015;

import java.util.List;
import java.util.function.Function;

public class PrismToVolume implements Function<List<Side>, Integer> {
    @Override
    public Integer apply(final List<Side> sides) {
        return sides.stream().reduce(0,
                (left, right) -> left + (2 * right.area()),
                (left, right) -> left);
    }
}
