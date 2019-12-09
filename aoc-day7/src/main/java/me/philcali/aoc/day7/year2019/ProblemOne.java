package me.philcali.aoc.day7.year2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;
import me.philcali.aoc.common.intcode.Mode;

@Year(2019)
@Day(7)
@Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    // Just keep copyig dis
    class Program {
        final List<Integer> memory;

        public Program(List<Integer> memory) {
            this.memory = memory;
        }

        public int run(final int... inputs) {
            int pointer = 0;
            int inputIndex = 0;
            int output = 0;
            try {
                while (pointer < memory.size()) {
                    final String instruction = String.format("%05d", memory.get(pointer++));
                    final int opscode = Integer
                            .parseInt(new String(new char[] { instruction.charAt(3), instruction.charAt(4) }));
                    final Mode[] parameterModes = new Mode[3];
                    for (int index = instruction.length() - 3; index >= 0; index--) {
                        parameterModes[2 - index] = Mode.fromValue(instruction.charAt(index) - 48);
                    }
                    switch (opscode) {
                    case 1:
                        final int addLeft = parameterModes[0].read(memory.get(pointer++), memory);
                        final int addRight = parameterModes[1].read(memory.get(pointer++), memory);
                        parameterModes[2].write(memory.get(pointer++), addLeft + addRight, memory);
                        break;
                    case 2:
                        final int multiLeft = parameterModes[0].read(memory.get(pointer++), memory);
                        final int multiRight = parameterModes[1].read(memory.get(pointer++), memory);
                        parameterModes[2].write(memory.get(pointer++), multiLeft * multiRight, memory);
                        break;
                    case 3:
                        System.out.println("Input " + inputs[inputIndex] + " at " + memory.get(pointer));
                        memory.set(memory.get(pointer++), inputs[inputIndex++]);
                        break;
                    case 4:
                        output = parameterModes[0].read(memory.get(pointer++), memory);
                        System.out.println("Output " + output + " cursor at " + (pointer - 1));
                        break;
                    case 5:
                        final int jumpTrueLeft = parameterModes[0].read(memory.get(pointer++), memory);
                        final int jumpTrueRight = parameterModes[1].read(memory.get(pointer++), memory);
                        if (jumpTrueLeft != 0) {
                            pointer = jumpTrueRight;
                        }
                        break;
                    case 6:
                        final int jumpFalseLeft = parameterModes[0].read(memory.get(pointer++), memory);
                        final int jumpFalseRight = parameterModes[1].read(memory.get(pointer++), memory);
                        if (jumpFalseLeft == 0) {
                            pointer = jumpFalseRight;
                        }
                        break;
                    case 7:
                        final int lessLeft = parameterModes[0].read(memory.get(pointer++), memory);
                        final int lessRight = parameterModes[1].read(memory.get(pointer++), memory);
                        parameterModes[2].write(memory.get(pointer++), lessLeft < lessRight ? 1 : 0, memory);
                        break;
                    case 8:
                        final int equalsLeft = parameterModes[0].read(memory.get(pointer++), memory);
                        final int equalsRight = parameterModes[1].read(memory.get(pointer++), memory);
                        parameterModes[2].write(memory.get(pointer++), equalsLeft == equalsRight ? 1 : 0, memory);
                        break;
                    case 99:
                        throw new RuntimeException("Halting");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return output;
        }
    }

    private void generator(List<List<Integer>> permutations, List<Integer> permutation, int[] values, int index) {
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

    private List<List<Integer>> generateSettingPermutations(final int maxSize) {
        final List<List<Integer>> permutations = new ArrayList<>();
        final int[] possibleValues = IntStream.range(0, maxSize).toArray();
        final List<Integer> permutation = new ArrayList<>();
        Arrays.stream(possibleValues).forEach(e -> permutation.add(0));
        generator(permutations, permutation, possibleValues, 0);
        return permutations;
    }

    @Override
    public void run() {
        final List<Integer> instructions = Arrays.stream(readLines().get(0).split(","))
                .map(code -> Integer.parseInt(code)).collect(Collectors.toList());
        final List<List<Integer>> permutations = generateSettingPermutations(5);
        int maxThrusterSignal = 0;
        for (final List<Integer> inputs : permutations) {
            int input = 0;
            for (final int setting : inputs) {
                final Program program = new Program(new ArrayList<>(instructions));
                input = program.run(new int[] { setting, input });
            }
            if (input > maxThrusterSignal) {
                maxThrusterSignal = input;
            }
        }
        System.out.println("Max thruster signal: " + maxThrusterSignal);
    }
}
