package me.philcali.aoc.day15;

import me.philcali.aoc.common.geometry.Point;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface Node extends Comparable<Node> {
    @NonNull
    int steps();
    @NonNull
    Point point();
    @Override
    default int compareTo(final Node other) {
        return Integer.compare(steps(), other.steps());
    }
}
