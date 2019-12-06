package me.philcali.aoc.day5.year2019;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Year(2019) @Day(5) @Problem(2)
@AutoService(DailyEvent.class)
public class ProblemTwo implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final List<Integer> instructions = Arrays.stream(readLines().get(0).split(","))
                .map(code -> Integer.parseInt(code))
                .collect(Collectors.toList());
        int pointer = 0;
        int display = 5;
        try {
            while (pointer < instructions.size()) {
                final String instruction = String.format("%05d", instructions.get(pointer++));
                final int opscode = Integer.parseInt(new String(new char[] {
                        instruction.charAt(3),
                        instruction.charAt(4)
                }));
                final Mode[] parameterModes = new Mode[3];
                for (int index = instruction.length() - 3; index >= 0; index--) {
                    parameterModes[2 - index] = Mode.fromValue(instruction.charAt(index) - 48);
                }
                switch (opscode) {
                case 1:
                    final int addLeft = parameterModes[0].read(instructions.get(pointer++), instructions);
                    final int addRight = parameterModes[1].read(instructions.get(pointer++), instructions);
                    parameterModes[2].write(instructions.get(pointer++), addLeft + addRight, instructions);
                    break;
                case 2:
                    final int multiLeft = parameterModes[0].read(instructions.get(pointer++), instructions);
                    final int multiRight = parameterModes[1].read(instructions.get(pointer++), instructions);
                    parameterModes[2].write(instructions.get(pointer++), multiLeft * multiRight, instructions);
                    break;
                case 3:
                    System.out.println("Input " + display + " at " + instructions.get(pointer));
                    instructions.set(instructions.get(pointer++), display);
                    break;
                case 4:
                    display = parameterModes[0].read(instructions.get(pointer++), instructions);
                    System.out.println("Output " + display + " cursor at " + (pointer - 1));
                    break;
                case 5:
                    final int jumpTrueLeft = parameterModes[0].read(instructions.get(pointer++), instructions);
                    final int jumpTrueRight = parameterModes[1].read(instructions.get(pointer++), instructions);
                    if (jumpTrueLeft != 0) {
                        pointer = jumpTrueRight;
                    }
                    break;
                case 6:
                    final int jumpFalseLeft = parameterModes[0].read(instructions.get(pointer++), instructions);
                    final int jumpFalseRight = parameterModes[1].read(instructions.get(pointer++), instructions);
                    if (jumpFalseLeft == 0) {
                        pointer = jumpFalseRight;
                    }
                    break;
                case 7:
                    final int lessLeft = parameterModes[0].read(instructions.get(pointer++), instructions);
                    final int lessRight = parameterModes[1].read(instructions.get(pointer++), instructions);
                    parameterModes[2].write(instructions.get(pointer++), lessLeft < lessRight ? 1 : 0, instructions);
                    break;
                case 8:
                    final int equalsLeft = parameterModes[0].read(instructions.get(pointer++), instructions);
                    final int equalsRight = parameterModes[1].read(instructions.get(pointer++), instructions);
                    parameterModes[2].write(instructions.get(pointer++), equalsLeft == equalsRight ? 1 : 0, instructions);
                    break;
                case 99:
                    throw new RuntimeException("Halting");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(instructions);
    }
}
