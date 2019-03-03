package me.philcali.aoc.day4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SleepActivityToQueue implements Function<Map<String, List<GuardSleepEvent>>, PriorityQueue<SelectedGuardEvent>> {
    @Override
    public PriorityQueue<SelectedGuardEvent> apply(final Map<String, List<GuardSleepEvent>> sleepActivity) {
        final PriorityQueue<SelectedGuardEvent> queue = new PriorityQueue<>();
        for (final Map.Entry<String, List<GuardSleepEvent>> entry : sleepActivity.entrySet()) {
            int totalTime = 0;
            final Map<Integer, Integer> minutesToCount = new HashMap<>();
            for (final GuardSleepEvent event : entry.getValue()) {
                totalTime += event.totalMinutes();
                IntStream.range(event.startTime().getMinute(), event.endTime().getMinute()).forEach(minute -> {
                    minutesToCount.compute(minute, (m, count) -> Objects.isNull(count) ? 1 : count + 1);
                });
            }
            int mostOccurance = 0;
            int mostMinutes = 0;
            for (final Map.Entry<Integer, Integer> countEntry : minutesToCount.entrySet()) {
                if (countEntry.getValue() > mostOccurance) {
                    mostOccurance = countEntry.getValue();
                    mostMinutes = countEntry.getKey();
                }
            }
            queue.offer(SelectedGuardEventData.builder()
                    .guardId(entry.getKey())
                    .minuteSleptTheMost(mostMinutes)
                    .totalTimeAsleep(totalTime)
                    .streak(mostOccurance)
                    .build());
        }
        return queue;
    }
}
