package me.philcali.aoc.day14;

import java.util.List;
import java.util.stream.IntStream;

public final class Scoreboard {
    public static int brew(final List<Integer> elves, final StringBuilder recipes) {
        final int brew = elves.stream()
                .map(recipes::charAt)
                .map(Character::getNumericValue)
                .reduce(Integer::sum).orElse(0);
        Integer.toString(brew).chars().map(Character::getNumericValue).forEach(recipes::append);
        IntStream.range(0, elves.size()).forEach(index -> {
            int pointer = elves.get(index) + Character.getNumericValue(recipes.charAt(elves.get(index))) + 1;
            if (pointer >= recipes.length()) {
                pointer = pointer % recipes.length();
            }
            elves.set(index, pointer);
        });
        return brew;
    }
}
