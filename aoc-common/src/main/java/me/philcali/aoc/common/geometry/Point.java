package me.philcali.aoc.common.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface Point extends Comparable<Point> {
    @NonNull
    int x();
    @NonNull
    int y();

    default int distance(final Point point) {
        return Math.abs(x() - point.x()) + Math.abs(y() - point.y());
    }

    default List<Point> radial(final int distance) {
        final List<Point> points = new ArrayList<>();
        IntStream.range(0, distance).forEach(between -> {
            points.addAll(Arrays.asList(
                    new PointData(x() + distance - between, y() + between),
                    new PointData(x() - distance + between, y() - between),
                    new PointData(x() + between, y() - distance + between),
                    new PointData(x() - between, y() + distance - between)));
        });
        return points;
    }

    @Override
    default int compareTo(final Point other) {
        final int y = Integer.compare(y(), other.y());
        if (y == 0) {
            return Integer.compare(x(), other.x());
        }
        return y;
    }
}
