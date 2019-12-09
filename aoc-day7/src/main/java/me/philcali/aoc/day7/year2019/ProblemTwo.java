package me.philcali.aoc.day7.year2019;

import java.util.Arrays;
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

@Year(2019) @Problem(2) @Day(7)
@AutoService(DailyEvent.class)
public class ProblemTwo implements AnnotatedDailyEvent, DailyInputEvent {

    @Override
    public void run() {
        final List<Integer> instructions = Arrays.stream(readLines().get(0).split(","))
                .map(code -> Integer.parseInt(code)).collect(Collectors.toList());
        final List<List<Integer>> permutations = SettingPermutations.create(5, 10);
        int maxThrusterSignal = 0;
        for (final List<Integer> inputs : permutations) {
            final List<Program> amplifiers = IntStream.range(0, 5)
                    .mapToObj(i -> new Program(instructions).snapshot()
                            .addInput(inputs.get(i)))
                    .collect(Collectors.toList());
            amplifiers.get(0).addInput(0);
            int amplifierIndex = 0;
            int finalSignal = 0;
            while (true) {
                try {
                    final Program program = amplifiers.get(amplifierIndex++);
                    final int output = program.run();
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
