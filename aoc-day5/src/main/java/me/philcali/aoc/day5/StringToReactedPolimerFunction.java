package me.philcali.aoc.day5;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Predicate;

public class StringToReactedPolimerFunction implements Function<InputStream, Stack<Character>> {
    private final Predicate<Character> filter;

    public StringToReactedPolimerFunction(final Predicate<Character> filter) {
        this.filter = filter;
    }

    public StringToReactedPolimerFunction() {
        this(c -> true);
    }

    private boolean isPolimer(final char letter, final char test) {
        return Math.abs(letter - test) == 32;
    }

    @Override
    public Stack<Character> apply(final InputStream input) {
        final Stack<Character> backtrace = new Stack<>();
        try (final Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            int readLetter = reader.read();
            while (readLetter != -1) {
                final char letter = (char) readLetter;
                if (!filter.test(letter)) {
                    readLetter = reader.read();
                    continue;
                }
                if (!backtrace.empty() && isPolimer(letter, backtrace.peek())) {
                    backtrace.pop();
                    readLetter = reader.read();
                    continue;
                }
                if (Character.isLetter(letter)) {
                    backtrace.push(letter);
                }
                readLetter = reader.read();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return backtrace;
    }
}
