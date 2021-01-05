package me.philcali.aoc.day3.year2020;

import com.google.auto.service.AutoService;
import me.philcali.aoc.common.*;
import me.philcali.aoc.common.geometry.Velocity;
import me.philcali.aoc.common.geometry.VelocityData;

import java.util.Arrays;
import java.util.List;

@Year(2020) @Day(3) @Problem(2)
@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent, AnnotatedDailyEvent {
    @Override
    public void run() {
        final List<Velocity> velocities = Arrays.asList(
                new VelocityData(1, 1),
                new VelocityData(3, 1),
                new VelocityData(5, 1),
                new VelocityData(7, 1),
                new VelocityData(1, 2)
        );
        final Toboggan toboggan = Toboggan.fromInput(readLines());
        System.out.println("The answer is: " + velocities.stream()
                .map(toboggan::treesOnTrajectory)
                .reduce(1L, (left, right) -> left * right));
    }
}
