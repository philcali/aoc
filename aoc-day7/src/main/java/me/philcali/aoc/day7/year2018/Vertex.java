package me.philcali.aoc.day7.year2018;

import java.util.List;
import java.util.stream.Collectors;

import me.philcali.aoc.day7.year2018.Edge.Direction;
import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.NonNull;
import me.philcali.zero.lombok.annotation.ToString;

@Builder @ToString
public interface Vertex extends Comparable<Vertex> {
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
}
