package me.philcali.aoc.day2.year2015;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface Prism {
    @NonNull
    int width();
    @NonNull
    int height();
    @NonNull
    int length();

    default List<Side> sides() {
        final List<Side> sides = Arrays.asList(
                new SideData(width(), length()),
                new SideData(height(), length()),
                new SideData(width(), height()));
        Collections.sort(sides);
        return sides;
    }

    default int volume() {
        return sides().stream().reduce(0,
                (left, right) -> left + (2 * right.area()),
                (left, right) -> left);
    }

    static Prism fromLine(final String line) {
        final String[] parts = line.split("x");
        final int l = Integer.parseInt(parts[0]);
        final int w = Integer.parseInt(parts[1]);
        final int h = Integer.parseInt(parts[2]);
        return new PrismData(l, w, h);
    }
}
