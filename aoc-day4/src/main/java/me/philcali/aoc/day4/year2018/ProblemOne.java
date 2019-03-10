package me.philcali.aoc.day4.year2018;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Function;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Day(4) @Problem(1) @Year(2018)
@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent, AnnotatedDailyEvent {
    private final Function<List<String>, Map<String, List<GuardSleepEvent>>> sleepActivity;

    public ProblemOne(final Function<List<String>, Map<String, List<GuardSleepEvent>>> sleepActivity) {
        this.sleepActivity = sleepActivity;
    }

    public ProblemOne() {
        this(new GuardToSleepActivityFunction());
    }

    @Override
    public void run() {
        long longestSleep = 0;
        SelectedGuardEvent chosenGuard = null;
        final PriorityQueue<SelectedGuardEvent> queue = sleepActivity.andThen(new SleepActivityToQueue()).apply(readLines());
        while (!queue.isEmpty()) {
            final SelectedGuardEvent event = queue.poll();
            if (event.totalTimeAsleep() > longestSleep) {
                chosenGuard = event;
                longestSleep = chosenGuard.totalTimeAsleep();
            }
        }
        System.out.println("Guard " + chosenGuard.guardId() + " slept the longest with " + longestSleep + " minutes");
        System.out.println("Guard " + chosenGuard.guardId() + " slept the most on minute " + chosenGuard.minuteSleptTheMost());
        System.out.println("Therefore the value is guardId * most minute: " + Integer.parseInt(chosenGuard.guardId()) * chosenGuard.minuteSleptTheMost());
    }
}
