package me.philcali.aoc.common.intcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Program {
    private final List<Long> memory;
    private int pointer = 0;
    private int relativeBase = 0;
    private final Queue<Long> inputs;

    public Program(List<Long> memory) {
        this.memory = memory;
        this.inputs = new LinkedList<>();
    }

    public Program snapshot() {
        return new Program(new ArrayList<>(memory));
    }

    public Program addInput(final long input) {
        this.inputs.add(input);
        return this;
    }

    public static Program fromLines(final List<String> lines) {
        return new Program(lines.stream()
                .flatMap(line -> Arrays.stream(line.split(",")))
                .map(number -> Long.parseLong(number))
                .collect(Collectors.toList()));
    }

    private long read(final int index, final Mode[] parameters) {
        return parameters[index].read(memory.get(pointer++), relativeBase, memory);
    }

    private void write(final int index, final Mode[] parameters, final long value) {
        parameters[index].write(memory.get(pointer++), relativeBase, value, memory);
    }

    public long run() {
        long output = 0;
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
                final long addLeft = read(0, parameterModes);
                final long addRight = read(1, parameterModes);
                write(2, parameterModes, addLeft + addRight);
                break;
            case 2:
                final long multiLeft = read(0, parameterModes);
                final long multiRight = read(1, parameterModes);
                write(2, parameterModes, multiLeft * multiRight);
                break;
            case 3:
                // System.out.println("Input " + input + " at " + memory.get(pointer));
                write(0, parameterModes, inputs.poll());
                break;
            case 4:
                output = read(0, parameterModes);
                System.out.println("Output " + output + " cursor at " + (pointer - 1));
                return output;
            case 5:
                final long jumpTrueLeft = read(0, parameterModes);
                final long jumpTrueRight = read(1, parameterModes);
                if (jumpTrueLeft != 0) {
                    pointer = (int) jumpTrueRight;
                }
                break;
            case 6:
                final long jumpFalseLeft = read(0, parameterModes);
                final long jumpFalseRight = read(1, parameterModes);
                if (jumpFalseLeft == 0) {
                    pointer = (int) jumpFalseRight;
                }
                break;
            case 7:
                final long lessLeft = read(0, parameterModes);
                final long lessRight = read(1, parameterModes);
                write(2, parameterModes, lessLeft < lessRight ? 1: 0);
                break;
            case 8:
                final long equalsLeft = read(0, parameterModes);
                final long equalsRight = read(1, parameterModes);
                write(2, parameterModes, equalsLeft == equalsRight ? 1 : 0);
                break;
            case 9:
                final long adjustValue = read(0, parameterModes);
                relativeBase += adjustValue;
                break;
            default:
                throw new ProgramHaltsException();
            }
        }
        return output;
    }
}
