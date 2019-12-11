package me.philcali.aoc.day7.year2019;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;
import me.philcali.aoc.common.intcode.Program;
import me.philcali.aoc.common.intcode.ProgramHaltsException;

@Year(2019) @Problem(2) @Day(7)
@AutoService(DailyEvent.class)
public class ProblemTwo implements AnnotatedDailyEvent, DailyInputEvent {

    @Override
    public void run() {
        final Program initialProgram = Program.fromLines(readLines());
        final List<List<Integer>> permutations = SettingPermutations.create(5, 10);
        long maxThrusterSignal = 0;
        for (final List<Integer> inputs : permutations) {
            final List<Program> amplifiers = IntStream.range(0, 5)
                    .mapToObj(i -> initialProgram.snapshot()
                            .addInput(inputs.get(i)))
                    .collect(Collectors.toList());
            amplifiers.get(0).addInput(0);
            int amplifierIndex = 0;
            long finalSignal = 0;
            while (true) {
                try {
                    final Program program = amplifiers.get(amplifierIndex++);
                    final long output = program.run();
                    if (amplifierIndex == amplifiers.size()) {
                        amplifierIndex = 0;
                        finalSignal = output;
                    }
                    amplifiers.get(amplifierIndex).addInput(output);
                } catch (ProgramHaltsException e) {
                    break;
                }
            }
            if (finalSignal > maxThrusterSignal) {
                maxThrusterSignal = finalSignal;
            }
        }
        System.out.println("Max thruster signal: " + maxThrusterSignal);
    }

}
