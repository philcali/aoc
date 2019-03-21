package me.philcali.aoc.day7.year2018;

import java.util.concurrent.atomic.AtomicInteger;

import me.philcali.zero.lombok.annotation.Builder;

@Builder
public interface Worker {
    Vertex vertex();

    AtomicInteger remainingWork();

    default boolean isDone() {
        return remainingWork().decrementAndGet() == 0;
    }
}
