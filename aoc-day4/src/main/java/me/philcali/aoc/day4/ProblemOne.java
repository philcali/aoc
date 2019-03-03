package me.philcali.aoc.day4;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;

@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent {
    private static final Pattern ENTRY_REGEX = Pattern.compile("^\\[([^\\]]+)\\]\\s+(.+)$");
    private static final Pattern GUARD_ID = Pattern.compile("Guard #(\\d+)");
    private static final String TIMESTAMP = "yyyy-MM-dd HH:mm";

    @Override
    public int day() {
        return 4;
    }

    @Override
    public int problem() {
        return 1;
    }

    @Override
    public void run() {
        final DateTimeFormatter format = DateTimeFormatter.ofPattern(TIMESTAMP);
        // min-heap this bad boy
        final PriorityQueue<GuardRotationEvent> queue = new PriorityQueue<>();
        for (final String line : readLines()) {
            final Matcher matcher = ENTRY_REGEX.matcher(line);
            if (matcher.matches()) {
                final Matcher guardId = GUARD_ID.matcher(matcher.group(2));
                final GuardRotationEvent event = GuardRotationEventData.builder()
                        .timestamp(LocalDateTime.parse(matcher.group(1), format))
                        .action(GuardRotationEvent.Action.fromLine(matcher.group(2)))
                        .guardId(Optional.of(guardId).filter(Matcher::find).map(m -> m.group(1)))
                        .build();
                queue.offer(event);
            }
        }
        String currentGuard = null;
        GuardSleepEventData.Builder builder = null;
        final Map<String, List<GuardSleepEvent>> recordedSleepCycles = new HashMap<>();
        while (!queue.isEmpty()) {
            final GuardRotationEvent event = queue.poll();
            switch (event.action()) {
            case BEGINS_SHIFT:
                if (!event.guardId().get().equals(currentGuard)) {
                    currentGuard = event.guardId().get();
                }
                break;
            case FALLS_ASLEEP:
                builder = GuardSleepEventData.builder().startTime(event.timestamp());
                break;
            case WAKES_UP:
                recordedSleepCycles.compute(currentGuard, (cg, cycles) -> {
                    return Optional.ofNullable(cycles).orElseGet(ArrayList::new);
                }).add(builder.endTime(event.timestamp()).build());
                break;
            default:
                break;
            }
        }
        long longestSleep = 0;
        int mostMinutes = 0;
        for (final Map.Entry<String, List<GuardSleepEvent>> entry : recordedSleepCycles.entrySet()) {
            final long totalSleep = entry.getValue().stream()
                    .map(e -> e.totalMinutes())
                    .reduce((x, y) -> x + y)
                    .get();
            if (totalSleep > longestSleep) {
                longestSleep = totalSleep;
                currentGuard = entry.getKey();
                final Map<Integer, Integer> minutesToCount = new HashMap<>();
                for (final GuardSleepEvent event : entry.getValue()) {
                    IntStream.range(event.startTime().getMinute(), event.endTime().getMinute()).forEach(minute -> {
                        minutesToCount.compute(minute, (m, count) -> Objects.isNull(count) ? 1 : count + 1);
                    });
                }
                int mostOccurance = 0;
                for (final Map.Entry<Integer, Integer> countEntry : minutesToCount.entrySet()) {
                    if (countEntry.getValue() > mostOccurance) {
                        mostMinutes = countEntry.getKey();
                        mostOccurance = countEntry.getValue();
                    }
                }
            }
        }
        System.out.println("Guard " + currentGuard + " slept the longest with " + longestSleep + " minutes");
        System.out.println("Guard " + currentGuard + " slept the most on minute " + mostMinutes);
        System.out.println("Therefore the value is guardId * most minute: " + Integer.parseInt(currentGuard) * mostMinutes);
    }
}
