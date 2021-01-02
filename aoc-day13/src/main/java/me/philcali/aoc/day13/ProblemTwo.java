package me.philcali.aoc.day13;

import java.util.stream.Collectors;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@AutoService(DailyEvent.class)
@Problem(2) @Day(13) @Year(2018)
public class ProblemTwo implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final Mine mine = Mine.fromLayout(readLines());
        do {
            for (final Cart cart : mine.carts().stream().collect(Collectors.toList())) {
                if (!mine.carts().contains(cart)) {
                    continue;
                }
                final Cart newCart = cart.move(mine.track().get(cart.coord()));
                if (mine.collides(cart, newCart)) {
                    mine.locations().remove(newCart.coord());
                    mine.carts().removeIf(c -> c.equals(cart) || newCart.coord().equals(c.coord()));
                } else {
                    mine.update(cart, newCart);
                }
            }
        } while (mine.carts().size() > 1);
        System.out.println(mine.carts());
    }
}
