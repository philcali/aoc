package me.philcali.aoc.common.geometry;

import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface Velocity {
    @NonNull
    int right();
    @NonNull
    int down();
}
