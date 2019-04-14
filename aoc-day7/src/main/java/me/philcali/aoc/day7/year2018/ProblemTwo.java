package me.philcali.aoc.day7.year2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;
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

@Day(7) @Problem(2) @Year(2018)
@AutoService(DailyEvent.class)
public class ProblemTwo implements AnnotatedDailyEvent, DailyInputEvent {
    private static final int BASE_SECONDS = 60;
    private static final int POOL_SIZE = 5;

    private void fillThreadPool(
            final List<Worker> threadPool,
            final PriorityQueue<Vertex> queue,
            final Map<Character, Worker> workLog) {
        // Attempt to offer new work
        while (!queue.isEmpty() && threadPool.size() < POOL_SIZE) {
            final Vertex vertex = queue.poll();
            if (!workLog.containsKey(vertex.value())) {
                threadPool.add(WorkerData.builder()
                        .vertex(vertex)
                        .remainingWork(new AtomicInteger(BASE_SECONDS + vertex.process()))
                        .build());
            }
        }
    }

    @Override
    public void run() {
        final Map<Character, Worker> workLog = new HashMap<>();
        final Graph graph = Graph.fromLines(readLines());
        final PriorityQueue<Vertex> queue = graph.topologicalSort();
        final List<Worker> threadPool = new ArrayList<>(POOL_SIZE);
        fillThreadPool(threadPool, queue, workLog);
        int totalTime = 0;
        for (; !threadPool.isEmpty(); totalTime++) {
            // Let's do the work here and offer new nodes whenever we're done
            final Iterator<Worker> workers = threadPool.iterator();
            while (workers.hasNext()) {
                final Worker worker = workers.next();
                if (worker.isDone()) {
                    workers.remove();
                    workLog.put(worker.vertex().value(), worker);
                    for (final Edge inEdge : worker.vertex().edgesFor(Direction.IN)) {
                        final Vertex destination = inEdge.destination();
                        if (workLog.keySet().containsAll(destination.edgesFor(Direction.OUT).stream()
                                .map(Edge::destination)
                                .map(Vertex::value)
                                .collect(Collectors.toList()))) {
                            queue.offer(destination);
                        }
                    }
                }
            }
            fillThreadPool(threadPool, queue, workLog);
        }
        System.out.println("Total time executing: " + totalTime);
    }
}
