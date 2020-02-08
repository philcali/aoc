package me.philcali.aoc.day10.year2019;

import me.philcali.aoc.common.geometry.Point;
import me.philcali.aoc.common.geometry.Velocity;
import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;

@Builder @Data
public interface Asteroid {
    Point point();

    Velocity velocity();
}
