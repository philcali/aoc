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
import me.philcali.aoc.common.intcode.Mode;

@Problem(1) @Year(2019) @Day(5)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final List<Integer> instructions = Arrays.stream(readLines().get(0).split(","))
                .map(code -> Integer.parseInt(code))
                .collect(Collectors.toList());
        int pointer = 0;
        int display = 1;
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
