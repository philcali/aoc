package me.philcali.aoc.day8.year2018;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.ToString;

@Builder @ToString
public interface Node {
    List<Integer> metadata();
    List<Node> children();

    default int sum() {
        return metadata().stream().reduce(0, (left, right) -> left + right);
    }

    static Node fromInput(final Scanner scanner) {
        final int childrenCount = scanner.nextInt();
        final int metadataCount = scanner.nextInt();
        final NodeData.Builder builder = NodeData.builder().addChildren().addMetadata();
        IntStream.range(0, childrenCount).forEach(time -> {
            builder.addChildren(Node.fromInput(scanner));
        });
        IntStream.range(0, metadataCount).forEach(time -> {
            builder.addMetadata(scanner.nextInt());
        });
        return builder.build();
    }
}
