package me.philcali.aoc.day7.year2018;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.NonNull;

@Builder
public interface Edge {
    enum Direction {
        IN, OUT;
    }
    @NonNull
    Direction direction();
    @NonNull
    Vertex destination();
}
