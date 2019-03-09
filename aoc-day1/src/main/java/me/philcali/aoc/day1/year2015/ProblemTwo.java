package me.philcali.aoc.day1.year2015;

import java.util.concurrent.atomic.AtomicInteger;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;

@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent {
    @Override
    public int day() {
        return 1;
    }

    @Override
    public int problem() {
        return 2;
    }

    @Override
    public int year() {
        return 2015;
    }

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
