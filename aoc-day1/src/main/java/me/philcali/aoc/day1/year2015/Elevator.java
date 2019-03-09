package me.philcali.aoc.day1.year2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Elevator implements BiFunction<InputStream, BiConsumer<Integer, Integer>, Integer> {
    @Override
    public Integer apply(final InputStream input, BiConsumer<Integer, Integer> thunk) {
        final Map<Character, Function<Integer, Integer>> functions = new HashMap<>();
        functions.put('(', level -> level + 1);
        functions.put(')', level -> level - 1);
        int destination = 0;
        int position = 1;
        try (final Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            int letter = reader.read();
            while (letter != -1) {
                final char paran = (char) letter;
                destination = functions.get(paran).apply(destination);
                thunk.accept(position++, destination);
                letter = reader.read();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return destination;
    }

    public Integer apply(final InputStream stream) {
        return apply(stream, (index, destination) -> {
            if (index % 1000 == 0) {
                System.out.println("Index " + index + ": " + destination);
            }
        });
    }
}
