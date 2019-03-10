package me.philcali.aoc.day8.year2018;

import java.util.List;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.ToString;

@Builder @ToString
public interface Node {
    List<Integer> metadata();
    List<Node> children();

    default int sum() {
        final int metasum = metadata().stream().reduce(0, (left, right) -> left + right);
        return children().stream().reduce(metasum,
                (left, right) -> left + right.sum(),
                (left, right) -> left);
    }
}
