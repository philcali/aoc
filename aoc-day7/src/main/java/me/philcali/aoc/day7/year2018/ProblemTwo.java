package me.philcali.aoc.day7.year2018;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;
import me.philcali.aoc.day7.year2018.Edge.Direction;

@Day(7) @Problem(2) @Year(2018)
@AutoService(DailyEvent.class)
public class ProblemTwo implements AnnotatedDailyEvent, DailyInputEvent {
    private static final int BASE_SECONDS = 60;
    private static final int POOL_SIZE = 5;

    @Override
    public void run() {
        final Graph graph = Graph.fromLines(readLines());
        final PriorityQueue<Vertex> queue = graph.prepareOrderedTraversal();
        final Set<Character> seen = new HashSet<>();
        final List<Worker> threadPool = new ArrayList<>(POOL_SIZE);
        int totalTime = 0;
        while (!queue.isEmpty() || !threadPool.isEmpty()) {
            totalTime++;
            // Let's do the work here and offer new nodes whenever we're done
            final Iterator<Worker> workers = threadPool.iterator();
            while (workers.hasNext()) {
                final Worker worker = workers.next();
                if (worker.isDone()) {
                    workers.remove();
                    for (final Edge inEdge : worker.vertex().edgesFor(Direction.IN)) {
                        final Vertex destination = inEdge.destination();
                        if (seen.containsAll(destination.edgesFor(Direction.OUT).stream()
                                .map(Edge::destination)
                                .map(Vertex::value)
                                .collect(Collectors.toList()))) {
                            queue.offer(destination);
                        }
                    }
                }
            }
            // We cannot proceed, let the workers in the threadPool do the work required.
            if (threadPool.size() == POOL_SIZE) {
                continue;
            }
            // Attempt to offer new work
            final Vertex vertex = queue.poll();
            if (Objects.nonNull(vertex) && seen.add(vertex.value())) {
                threadPool.add(WorkerData.builder()
                        .vertex(vertex)
                        .remainingWork(new AtomicInteger(BASE_SECONDS + vertex.process()))
                        .build());
            }
        }
        System.out.println("Total time executing: " + totalTime);
    }
}
