package me.philcali.aoc.day3.year2020;

import me.philcali.aoc.common.geometry.Point;
import me.philcali.aoc.common.geometry.PointData;
import me.philcali.aoc.common.geometry.Velocity;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
public interface Toboggan {
    @NonNull
    char[][] slope();

    default Plot get(final Point point) {
        return new PlotData(slope()[point.y()][point.x()], point);
    }

    default List<Plot> trajectory(final Velocity velocity) {
        final List<Plot> path = new ArrayList<>();
        Point currentPosition = new PointData(0, 0);
        path.add(get(currentPosition));
        while (currentPosition.y() <= slope().length) {
            int newX = currentPosition.x() + velocity.right();
            int newY = currentPosition.y() + velocity.down();
            if (newX >= slope()[currentPosition.y()].length) {
                newX -= slope()[currentPosition.y()].length;
            }
            if (newY >= slope().length) {
                break;
            }
            currentPosition = new PointData(newX, newY);
            path.add(get(currentPosition));
        }
        return path;
    }

    default long treesOnTrajectory(final Velocity velocity) {
        return trajectory(velocity).stream().filter(Plot::isTree).count();
    }

    static Toboggan fromInput(final List<String> input) {
        final char[][] slope = new char[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            final char[] line = input.get(y).toCharArray();
            for (int x = 0; x < line.length; x++) {
                slope[y][x] = line[x];
            }
        }
        return new TobogganData(slope);
    }
}
