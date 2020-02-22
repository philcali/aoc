package me.philcali.aoc.day10.year2019;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.philcali.aoc.common.geometry.Point;
import me.philcali.aoc.common.geometry.PointData;

public final class Starfield {
    public static Set<Point> asteroids(final List<String> starField) {
        final Set<Point> asteroids = new HashSet<>();
        final char[][] map = new char[starField.size()][starField.size()];
        for (int y = 0; y < starField.size(); y++) {
            final String line = starField.get(y);
            for (int x = 0; x < starField.size(); x++) {
                final char data = line.charAt(x);
                if (data == '#') {
                    asteroids.add(new PointData(x, y));
                }
                map[x][y] = data;
            }
        }
        return asteroids;
    }
}
