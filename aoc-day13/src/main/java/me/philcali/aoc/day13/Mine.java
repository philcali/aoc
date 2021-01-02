package me.philcali.aoc.day13;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import me.philcali.aoc.common.geometry.Point;
import me.philcali.aoc.common.geometry.PointData;
import me.philcali.aoc.day13.Cart.Direction;
import me.philcali.aoc.day13.Cart.Option;
import me.philcali.zero.lombok.annotation.ConcreteType;
import me.philcali.zero.lombok.annotation.ConcreteTypes;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;
import me.philcali.zero.lombok.annotation.ToString;

@Data @ToString
@ConcreteTypes({
    @ConcreteType(contract = SortedSet.class, implementation = TreeSet.class),
    @ConcreteType(contract = Set.class, implementation = HashSet.class)
})
public interface Mine {
    @NonNull
    SortedSet<Cart> carts();
    @NonNull
    Map<Point, Track> track();
    @NonNull
    Set<Point> locations();

    default boolean collides(final Cart cart, final Cart newCart) {
        return this.locations().remove(cart.coord()) && !this.locations().add(newCart.coord());
    }

    default void update(final Cart cart, final Cart newCart) {
        this.carts().remove(cart);
        this.carts().add(newCart);
    }

    static Mine fromLayout(final List<String> trackLayout) {
        final Map<Point, Track> track = new HashMap<>();
        final Set<Point> locations = new HashSet<>();
        final SortedSet<Cart> carts = new TreeSet<>();
        int y = 0;
        for (final String line : trackLayout) {
            for (int x = 0; x < line.length(); x++) {
                final char layout = line.charAt(x);
                final Point coord = new PointData(x, y);
                track.put(coord, Track.fromLayout(track, coord, layout));
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
        return new MineData(carts, locations, track);
    }
}
