package me.philcali.aoc.day4;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Function;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;

@AutoService(DailyEvent.class)
public class ProblemTwo implements DailyInputEvent {
    private final Function<List<String>, Map<String, List<GuardSleepEvent>>> sleepActivity;

    public ProblemTwo(final Function<List<String>, Map<String, List<GuardSleepEvent>>> sleepActivity) {
        this.sleepActivity = sleepActivity;
    }

    public ProblemTwo() {
        this(new GuardToSleepActivityFunction());
    }

    @Override
    public int day() {
        return 4;
    }

    @Override
    public int problem() {
        return 2;
    }

    @Override
    public int year() {
        return 2018;
    }

    @Override
    public void run() {
        final PriorityQueue<SelectedGuardEvent> queue = sleepActivity.andThen(new SleepActivityToQueue()).apply(readLines());
        final SelectedGuardEvent event = queue.poll();
        System.out.println("Guard " + event.guardId() + " slept the most " + event.streak() + " on minute " + event.minuteSleptTheMost());
        System.out.println("Therefore guardId * minute slept the most = " + Integer.parseInt(event.guardId()) * event.minuteSleptTheMost());
    }
}
