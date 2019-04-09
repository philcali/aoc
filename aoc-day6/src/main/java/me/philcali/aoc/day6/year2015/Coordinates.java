package me.philcali.aoc.day6.year2015;

import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface Coordinates {
    @NonNull
    int x();
    @NonNull
    int y();

    static Coordinates fromString(final String input) {
        final String[] parts = input.split("\\s*,\\s*");
        return new CoordinatesData(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }
}
