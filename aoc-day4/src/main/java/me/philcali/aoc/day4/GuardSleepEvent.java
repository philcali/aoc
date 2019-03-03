package me.philcali.aoc.day4;

import java.time.Duration;
import java.time.LocalDateTime;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data @Builder
public interface GuardSleepEvent {
    @NonNull
    LocalDateTime startTime();
    @NonNull
    LocalDateTime endTime();

    default long totalMinutes() {
        return Duration.between(startTime(), endTime()).toMinutes();
    }
}
