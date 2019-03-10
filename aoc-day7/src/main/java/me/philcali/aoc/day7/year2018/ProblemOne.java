package me.philcali.aoc.day7.year2018;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;
import me.philcali.aoc.day7.year2018.Edge.Direction;

@Day(7) @Problem(1) @Year(2018)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    private static final Pattern STEPS_REGEX = Pattern.compile("^Step ([A-Z]).*step ([A-Z]).+$");

    @Override
    public void run() {
        final Graph graph = new Graph();
        for (final String line : readLines()) {
            final Matcher matcher = STEPS_REGEX.matcher(line);
            if (matcher.matches()) {
                final Character leftValue = matcher.group(1).charAt(0);
                final Character rightValue = matcher.group(2).charAt(0);
                graph.edge(leftValue, rightValue);
            }
        }
        final Set<Character> seen = new HashSet<>();
        final PriorityQueue<Vertex> queue = new PriorityQueue<>();
        graph.vertices().stream().filter(v -> v.edgesFor(Direction.OUT).isEmpty()).forEach(queue::offer);
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
