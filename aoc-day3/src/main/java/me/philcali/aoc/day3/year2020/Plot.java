package me.philcali.aoc.day3.year2020;

import me.philcali.aoc.common.geometry.Point;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface Plot {
    @NonNull
    Point point();

    @NonNull
    char data();

    default boolean isTree() {
        return data() == '#';
    }
}
