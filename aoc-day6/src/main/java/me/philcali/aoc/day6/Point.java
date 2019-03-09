package me.philcali.aoc.day6;

import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface Point {
    @NonNull
    int x();
    @NonNull
    int y();

    default int distance(final Point point) {
        return Math.abs(x() - point.x()) + Math.abs(y() - point.y());
    }
}
