package me.philcali.aoc.common.geometry;

import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface Line extends Shape {
    @NonNull
    Point start();
    @NonNull
    Point end();

    @Override
    default boolean contains(final Point point) {
        return new LineData(start(), point).slope() == slope();
    }

    default double slope() {
        return Math.atan2(end().y() - start().y(), end().x() - start().x());
    }
}
