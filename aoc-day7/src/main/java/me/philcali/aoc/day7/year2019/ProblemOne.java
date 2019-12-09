package me.philcali.aoc.day7.year2019;

import java.util.ArrayList;
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

@Year(2019)
@Day(7)
@Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {

    @Override
    public void run() {
        final List<Integer> instructions = Arrays.stream(readLines().get(0).split(","))
                .map(code -> Integer.parseInt(code)).collect(Collectors.toList());
        final List<List<Integer>> permutations = SettingPermutations.create(0, 5);
        int maxThrusterSignal = 0;
        for (final List<Integer> inputs : permutations) {
            int input = 0;
            for (final int setting : inputs) {
                final Program program = new Program(new ArrayList<>(instructions));
                program.addInput(setting).addInput(input);
                input = program.run();
            }
            if (input > maxThrusterSignal) {
                maxThrusterSignal = input;
            }
        }
        System.out.println("Max thruster signal: " + maxThrusterSignal);
    }
}
