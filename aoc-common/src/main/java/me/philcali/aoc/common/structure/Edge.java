package me.philcali.aoc.common.structure;

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
