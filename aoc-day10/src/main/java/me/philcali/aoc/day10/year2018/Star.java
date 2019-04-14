package me.philcali.aoc.day10.year2018;

import me.philcali.aoc.common.geometry.Point;
import me.philcali.aoc.common.geometry.PointData;
import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data @Builder
public interface Star {
    @NonNull
    int id();
    @NonNull
    Velocity velocity();

    default public Point move(final Point coord) {
        return new PointData(coord.x() + velocity().right(), coord.y() + velocity().down());
    }
}
