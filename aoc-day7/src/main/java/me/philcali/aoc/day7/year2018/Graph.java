package me.philcali.aoc.day7.year2018;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import me.philcali.aoc.day7.year2018.Edge.Direction;

public class Graph {
    private final Map<Character, Vertex> vertices;

    public Graph(final Map<Character, Vertex> vertices) {
        this.vertices = vertices;
    }

    public Graph() {
        this(new HashMap<>());
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
