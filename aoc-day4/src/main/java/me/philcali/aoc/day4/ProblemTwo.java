package me.philcali.aoc.day4;

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

@Day(4) @Problem(2) @Year(2018)
@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent, AnnotatedDailyEvent {
    private final Function<List<String>, Map<String, List<GuardSleepEvent>>> sleepActivity;

    public ProblemTwo(final Function<List<String>, Map<String, List<GuardSleepEvent>>> sleepActivity) {
        this.sleepActivity = sleepActivity;
    }

    public ProblemTwo() {
        this(new GuardToSleepActivityFunction());
    }

    @Override
    public void run() {
        final PriorityQueue<SelectedGuardEvent> queue = sleepActivity.andThen(new SleepActivityToQueue()).apply(readLines());
        final SelectedGuardEvent event = queue.poll();
        System.out.println("Guard " + event.guardId() + " slept the most " + event.streak() + " on minute " + event.minuteSleptTheMost());
        System.out.println("Therefore guardId * minute slept the most = " + Integer.parseInt(event.guardId()) * event.minuteSleptTheMost());
    }
}
