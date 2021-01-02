package me.philcali.aoc.day13;

import java.util.Objects;
import java.util.stream.Collectors;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;
import me.philcali.aoc.common.geometry.Point;

@Problem(1) @Day(13) @Year(2018)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final Mine mine = Mine.fromLayout(readLines());
        Point collision = null;
        do {
            for (final Cart cart : mine.carts().stream().collect(Collectors.toList())) {
                final Cart newCart = cart.move(mine.track().get(cart.coord()));
                if (mine.collides(cart, newCart)) {
                    collision = newCart.coord();
                    break;
                }
                mine.update(cart, newCart);
            }
        } while (Objects.isNull(collision));
        System.out.println("First collision: " + collision);
    }
}
