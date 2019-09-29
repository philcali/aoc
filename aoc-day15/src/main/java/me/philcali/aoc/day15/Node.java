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
        final int steps = Integer.compare(steps(), other.steps());
        if (steps == 0) {
            return point().compareTo(other.point());
        }
        return steps;
    }
}
