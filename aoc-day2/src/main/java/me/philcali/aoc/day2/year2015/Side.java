package me.philcali.aoc.day2.year2015;

import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface Side extends Comparable<Side> {
    @NonNull
    int width();
    @NonNull
    int height();

    default int area() {
        return width() * height();
    }

    @Override
    default int compareTo(final Side other) {
        return Integer.compare(area(), other.area());
    }
}
