package me.philcali.aoc.day7.year2018;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;
import me.philcali.aoc.common.structure.Edge;
import me.philcali.aoc.common.structure.Edge.Direction;
import me.philcali.aoc.common.structure.Graph;
import me.philcali.aoc.common.structure.Vertex;

@Day(7) @Problem(1) @Year(2018)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {

    @Override
    public void run() {
        final Set<Character> seen = new HashSet<>();
        final Graph graph = Graph.fromLines(readLines());
        final PriorityQueue<Vertex> queue = graph.topologicalSort();
        while (!queue.isEmpty()) {
            final Vertex vertex = queue.poll();
            if (seen.add(vertex.value())) {
                System.out.print(vertex.value());
            }
            for (final Edge inEdge : vertex.edgesFor(Direction.IN)) {
                final Vertex destination = inEdge.destination();
                if (seen.containsAll(destination.edgesFor(Direction.OUT).stream()
                        .map(Edge::destination)
                        .map(Vertex::value)
                        .collect(Collectors.toList()))) {
                    queue.offer(inEdge.destination());
                }
            }
        }
        System.out.println();
    }
}
