package me.philcali.aoc.day2.year2016;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;

@Data @Builder
public interface Cell {
    boolean isOpaque();

    char value();

    static Cell empty() {
        return CellData.builder().withOpaque(true).build();
    }

    static Cell valued(final char value) {
        return CellData.builder().value(value).build();
    }
}
