package me.philcali.aoc.day15;

import java.util.List;

import me.philcali.aoc.common.geometry.Point;
import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.NonNull;
import me.philcali.zero.lombok.annotation.ToString;

@Builder @ToString
public interface TracedPath extends Comparable<TracedPath> {
    @Builder.Default
    int DEFAULT_STEPS = 0;
    @NonNull
    int steps();
    List<Point> line();
    @NonNull
    boolean routesToTarget();
    @Override
    default int compareTo(final TracedPath path) {
        final int distance = Integer.compare(steps(), path.steps());
        // Same distance
        if (distance == 0) {
            // Same target
            final Point destinationA = line().get(line().size() - 1);
            final Point destinationB = path.line().get(path.line().size() - 1);
            final int target = destinationA.compareTo(destinationB);
            if (target == 0) {
                // Read order choice
                final Point startingA = line().get(0);
                final Point startingB = path.line().get(0);
                return startingA.compareTo(startingB);
            }
            return target;
        }
        return distance;
    }
}
