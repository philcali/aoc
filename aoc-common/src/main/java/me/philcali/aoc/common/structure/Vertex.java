package me.philcali.aoc.common.structure;

import java.util.List;
import java.util.stream.Collectors;

import me.philcali.aoc.common.structure.Edge.Direction;
import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.NonNull;
import me.philcali.zero.lombok.annotation.ToString;

@Builder @ToString
public interface Vertex extends Comparable<Vertex> {
    int ASCII_DEFAULT = 64;

    @NonNull
    Character value();
    List<Edge> edges();

    default List<Edge> edgesFor(final Direction direction) {
        return edges().stream()
                .filter(e -> e.direction().equals(direction))
                .collect(Collectors.toList());
    }

    @Override
    default int compareTo(final Vertex other) {
        return value().compareTo(other.value());
    }

    default int process() {
        return value() - ASCII_DEFAULT;
    }
}
