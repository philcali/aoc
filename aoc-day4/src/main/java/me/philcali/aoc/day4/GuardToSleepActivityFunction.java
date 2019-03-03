package me.philcali.aoc.day4;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuardToSleepActivityFunction implements Function<List<String>, Map<String, List<GuardSleepEvent>>> {
    private static final Pattern ENTRY_REGEX = Pattern.compile("^\\[([^\\]]+)\\]\\s+(.+)$");
    private static final Pattern GUARD_ID = Pattern.compile("Guard #(\\d+)");
    private static final String TIMESTAMP = "yyyy-MM-dd HH:mm";

    @Override
    public Map<String, List<GuardSleepEvent>> apply(final List<String> lines) {
        final DateTimeFormatter format = DateTimeFormatter.ofPattern(TIMESTAMP);
        // min-heap this bad boy
        final PriorityQueue<GuardRotationEvent> queue = new PriorityQueue<>();
        for (final String line : lines) {
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
        return recordedSleepCycles;
    }
}
