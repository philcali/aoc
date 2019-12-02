package me.philcali.aoc.day2.year2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Program {
    private final List<Integer> input;

    public Program(final List<Integer> input) {
        this.input = input;
    }

    public static Program fromInput(final List<String> lines) {
        return new Program(Arrays.stream(lines.get(0).split(","))
                    .map(code -> Integer.parseInt(code))
                    .collect(Collectors.toList()));
    }

    public int output(final int noun, final int verb) {
        int codex = 0;
        final List<Integer> codes = new ArrayList<>(input);
        codes.set(1, noun);
        codes.set(2, verb);
        while (codex < codes.size()) {
            try {
                switch (codes.get(codex)) {
                case 1:
                    codes.set(codes.get(codex + 3),
                            codes.get(codes.get(codex + 1)) +
                            codes.get(codes.get(codex + 2)));
                    break;
                case 2:
                    codes.set(codes.get(codex + 3),
                            codes.get(codes.get(codex + 1)) *
                            codes.get(codes.get(codex + 2)));
                    break;

                default:
                    throw new RuntimeException("Encountered " + codes.get(codex) + " at " + codex);
                }
            } catch (RuntimeException e) {
                System.out.println("Ending program...");
                break;
            }
            codex += 4;
        }
        return codes.get(0);
    }
}
