package me.philcali.aoc.day7.year2019;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import me.philcali.aoc.common.intcode.Mode;

public class Program {
    private final List<Integer> memory;
    private int pointer = 0;
    private final Queue<Integer> inputs;

    public Program(List<Integer> memory) {
        this.memory = memory;
        this.inputs = new LinkedList<>();
    }

    public Program snapshot() {
        return new Program(new ArrayList<>(memory));
    }

    public Program addInput(final int input) {
        this.inputs.add(input);
        return this;
    }

    public int run() {
        int output = 0;
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
                int input = inputs.poll();
                System.out.println("Input " + input + " at " + memory.get(pointer));
                memory.set(memory.get(pointer++), input);
                break;
            case 4:
                output = parameterModes[0].read(memory.get(pointer++), memory);
                System.out.println("Output " + output + " cursor at " + (pointer - 1));
                return output;
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
            default:
                throw new ProgramHaltsException();
            }
        }
        return output;
    }
}
