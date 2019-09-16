package me.philcali.aoc.day13;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;
import me.philcali.aoc.common.geometry.Point;
import me.philcali.aoc.common.geometry.PointData;
import me.philcali.aoc.day13.Cart.Direction;
import me.philcali.aoc.day13.Cart.Option;

@Problem(1) @Day(13) @Year(2018)
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final Map<Point, Track> map = new HashMap<>();
        final Set<Point> locations = new HashSet<>();
        final SortedSet<Cart> carts = new TreeSet<>();
        int y = 0;
        for (final String line : readLines()) {
            for (int x = 0; x < line.length(); x++) {
                final char layout = line.charAt(x);
                final Point coord = new PointData(x, y);
                map.put(coord, Track.fromLayout(map, coord, layout));
                Direction.fromLayout(layout).ifPresent(direction -> {
                    locations.add(coord);
                    carts.add(CartData.builder()
                            .initial(coord)
                            .turnOption(Option.LEFT)
                            .direction(direction)
                            .coord(coord)
                            .build());
                });
            }
            y++;
        }
        Point collision = null;
        int ticks = 0;
        do {
            for (final Cart cart : carts.stream().collect(Collectors.toList())) {
                final Cart newCart = cart.move(map.get(cart.coord()));
                if (locations.remove(cart.coord()) && !locations.add(newCart.coord())) {
                    collision = newCart.coord();
                    break;
                }
                carts.remove(cart);
                carts.add(newCart);
            }
            ticks++;
        } while (Objects.isNull(collision));
        System.out.println("First collision: " + collision);
    }
}
