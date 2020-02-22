package me.philcali.aoc.day10.year2019;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import me.philcali.aoc.common.geometry.Line;
import me.philcali.aoc.common.geometry.LineData;
import me.philcali.aoc.common.geometry.Point;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface MonitoringStation extends Comparable<MonitoringStation> {
    @NonNull
    Point asteroid();
    @NonNull
    int visibleAsteroids();

    static MonitoringStation optimal(final Set<Point> asteroids) {
        final Queue<MonitoringStation> potentialStations = new PriorityQueue<>();
        for (final Point potentialBase : asteroids) {
            final Set<Double> inLineOfSight = new HashSet<>();
            final List<Point> closest = new ArrayList<>(asteroids);
            closest.remove(potentialBase);
            Collections.sort(closest, (left, right) -> {
                return Integer.compare(left.distance(potentialBase), right.distance(potentialBase));
            });
            for (final Point asteroid : closest) {
                final Line lineOfSight = new LineData(potentialBase, asteroid);
                if (!inLineOfSight.add(lineOfSight.slope())) {
                    System.out.println("Blocked! " + asteroid);
                }
            }
            potentialStations.add(new MonitoringStationData(inLineOfSight.size(), potentialBase));
        }
        return potentialStations.poll();
    }

    @Override
    default int compareTo(final MonitoringStation other) {
        return Integer.compare(other.visibleAsteroids(), visibleAsteroids());
    }
}
