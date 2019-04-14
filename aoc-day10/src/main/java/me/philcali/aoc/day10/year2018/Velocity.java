package me.philcali.aoc.day10.year2018;

import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface Velocity {
    @NonNull
    int right();
    @NonNull
    int down();
}
