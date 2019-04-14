package me.philcali.aoc.day10.year2018;

import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface Coordinates extends Comparable<Coordinates> {
    @NonNull
    int x();
    @NonNull
    int y();

    @Override
    default int compareTo(final Coordinates other) {
        return Integer.compare(y(), other.y());
    }
}
