package me.philcali.aoc.day1.year2015;

import java.util.concurrent.atomic.AtomicInteger;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Description;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(1) @Problem(2) @Year(2015)
@Description("Not Quite Lisp: First Basement Position")
@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent, AnnotatedDailyEvent {
    @Override
    public void run() {
        final AtomicInteger basement = new AtomicInteger();
        new Elevator().apply(streamInput(), (index, destination) -> {
            if (destination == -1 && basement.get() == 0) {
                basement.set(index);
            }
        });
        System.out.println("First basement position: " + basement.get());
    }
}
