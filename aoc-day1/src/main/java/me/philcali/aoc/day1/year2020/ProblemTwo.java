package me.philcali.aoc.day1.year2020;

import com.google.auto.service.AutoService;
import me.philcali.aoc.common.*;

@Year(2020) @Day(1) @Problem(2)
@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent, AnnotatedDailyEvent {
    @Override
    public void run() {
        final Calculator calc = CalculatorData.builder()
                .input(readLines())
                .iterations(3)
                .build();
        System.out.println("The answer is: " + calc.evaluate());
    }
}
