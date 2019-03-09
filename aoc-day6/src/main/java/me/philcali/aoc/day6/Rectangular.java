package me.philcali.aoc.day6;

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
}
