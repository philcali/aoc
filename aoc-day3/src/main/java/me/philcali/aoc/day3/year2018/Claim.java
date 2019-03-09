package me.philcali.aoc.day3.year2018;

import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface Claim {
    @NonNull
    int id();

    boolean isOverlaps();
}
