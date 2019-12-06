package me.philcali.aoc.day6.year2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Year(2019) @Day(6) @Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final Map<String, List<String>> orbits = new HashMap<>();
        for (final String line : readLines()) {
            final String[] objects = line.split("\\)");
            orbits.computeIfAbsent(objects[0], planet -> new ArrayList<>());
            orbits.compute(objects[1], (key, value) -> {
                final List<String> orbiting = Optional.ofNullable(value).orElseGet(ArrayList::new);
                orbiting.add(objects[0]);
                return orbiting;
            });
        }

        int totalOrbits = 0;
        for (final Map.Entry<String, List<String>> entry : orbits.entrySet()) {
            Queue<String> orbiters = new LinkedList<>(entry.getValue());
            String orbit = orbiters.poll();
            while (Objects.nonNull(orbit)) {
                totalOrbits++;
                orbiters.addAll(orbits.get(orbit));
                orbit = orbiters.poll();
            }
        }
        System.out.println("Total orbits: " + totalOrbits);
    }
}
