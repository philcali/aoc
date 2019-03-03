package me.philcali.aoc.day4;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data @Builder
public interface SelectedGuardEvent extends Comparable<SelectedGuardEvent> {
    @NonNull
    String guardId();
    @NonNull
    int minuteSleptTheMost();
    @NonNull
    int streak();
    @NonNull
    int totalTimeAsleep();

    @Override
    default int compareTo(final SelectedGuardEvent event) {
        return Integer.compare(event.streak(), streak());
    }
}
