package me.philcali.aoc.common.geometry;

import java.util.Set;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data @Builder
public interface Rectangular extends Shape {
    @NonNull
    Point topLeft();
    @NonNull
    Point topRight();
    @NonNull
    Point bottomLeft();
    @NonNull
    Point bottomRight();

    @Override
    default boolean contains(final Point point) {
        return point.x() >= topLeft().x()
                && point.x() >= bottomLeft().x()
                && point.x() <= topRight().x()
                && point.x() <= bottomRight().x()
                && point.y() >= topLeft().y()
                && point.y() <= bottomLeft().y()
                && point.y() >= topRight().y()
                && point.y() <= bottomRight().y();
    }

    default Point center() {
        final int width = Math.max(topRight().x(), bottomRight().x()) - Math.min(topLeft().x(), bottomLeft().x());
        final int height = Math.max(bottomRight().y(), bottomLeft().y()) - Math.min(topLeft().y(), topRight().y());
        return new PointData(width / 2, height / 2);
    }

    static Rectangular boundary(final Set<Point> points) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (final Point point : points) {
            if (point.x() < minX) {
                minX = point.x();
            }
            if (point.x() > maxX) {
                maxX = point.x();
            }
            if (point.y() < minY) {
                minY = point.y();
            }
            if (point.y() > maxY) {
                maxY = point.y();
            }
        }
        return RectangularData.builder()
                .topLeft(new PointData(minX, minY))
                .topRight(new PointData(maxX, minY))
                .bottomLeft(new PointData(minX, maxY))
                .bottomRight(new PointData(maxX, maxY))
                .build();
    }
}
