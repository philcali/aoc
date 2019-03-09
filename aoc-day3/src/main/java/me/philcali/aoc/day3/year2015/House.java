package me.philcali.aoc.day3.year2015;

import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface House {
    @NonNull
    int longitude();
    @NonNull
    int latitude();
}
