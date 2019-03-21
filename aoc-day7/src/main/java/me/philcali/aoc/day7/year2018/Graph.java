package me.philcali.aoc.day7.year2018;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.philcali.aoc.day7.year2018.Edge.Direction;

public class Graph {
    private static final Pattern STEPS_REGEX = Pattern.compile("^Step ([A-Z]).*step ([A-Z]).+$");
    private final Map<Character, Vertex> vertices;

    public Graph(final Map<Character, Vertex> vertices) {
        this.vertices = vertices;
    }

    public Graph() {
        this(new HashMap<>());
    }

    public static Graph fromLines(final List<String> lines) {
        final Graph graph = new Graph();
        for (final String line : lines) {
            final Matcher matcher = STEPS_REGEX.matcher(line);
            if (matcher.matches()) {
                final Character leftValue = matcher.group(1).charAt(0);
                final Character rightValue = matcher.group(2).charAt(0);
                graph.edge(leftValue, rightValue);
            }
        }
        return graph;
    }

    public PriorityQueue<Vertex> prepareOrderedTraversal() {
        final PriorityQueue<Vertex> queue = new PriorityQueue<>();
        vertices().stream().filter(v -> v.edgesFor(Direction.OUT).isEmpty()).forEach(queue::offer);
        return queue;
    }

    public Graph vertex(final Character node) {
        vertices.putIfAbsent(node, VertexData.builder().value(node).addEdges().build());
        return this;
    }

    public Graph edge(final Character source, final Character destination) {
        vertex(source).vertex(destination);
        vertices.get(source).edges().add(EdgeData.builder()
                .direction(Direction.IN)
                .destination(vertices.get(destination))
                .build());
        vertices.get(destination).edges().add(EdgeData.builder()
                .direction(Direction.OUT)
                .destination(vertices.get(source))
                .build());
        return this;
    }

    public Collection<Vertex> vertices() {
        return vertices.values();
    }
}
