package me.philcali.aoc.day10.year2018;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data @Builder
public interface Star {
    @NonNull
    int id();
    @NonNull
    Velocity velocity();

    default public Coordinates move(final Coordinates coord) {
        return new CoordinatesData(coord.x() + velocity().right(), coord.y() + velocity().down());
    }
}
