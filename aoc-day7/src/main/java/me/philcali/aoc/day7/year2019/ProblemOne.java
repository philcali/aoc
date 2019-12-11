package me.philcali.aoc.day7.year2019;

import java.util.List;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;
import me.philcali.aoc.common.intcode.Program;

@Year(2019)
@Day(7)
@Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {

    @Override
    public void run() {
        final Program initialProgram = Program.fromLines(readLines());
        final List<List<Integer>> permutations = SettingPermutations.create(0, 5);
        long maxThrusterSignal = 0;
        for (final List<Integer> inputs : permutations) {
            long input = 0;
            for (final int setting : inputs) {
                final Program program = initialProgram.snapshot();
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
