package me.philcali.aoc.day3.year2020;

import com.google.auto.service.AutoService;
import me.philcali.aoc.common.*;
import me.philcali.aoc.common.geometry.VelocityData;

@Year(2020) @Day(3) @Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent, AnnotatedDailyEvent {
    @Override
    public void run() {
        final Toboggan toboggan = Toboggan.fromInput(readLines());
        System.out.println("The answer is: " + toboggan.treesOnTrajectory(new VelocityData(3, 1)));
    }
}
