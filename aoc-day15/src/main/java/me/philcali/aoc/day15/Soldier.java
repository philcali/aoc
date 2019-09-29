package me.philcali.aoc.day15;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import me.philcali.aoc.common.geometry.Point;
import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Builder @Data
public interface Soldier extends Comparable<Soldier> {
    @Builder.Default
    int DEFAULT_HEALTH = 200;
    @Builder.Default
    int DEFAULT_STRENGTH = 3;

    @NonNull
    Point position();
    @NonNull
    int health();
    @NonNull
    int strength();
    @NonNull
    Race race();

    default boolean isDead() {
        return health() <= 0;
    }

    @Override
    default int compareTo(final Soldier other) {
        return position().compareTo(other.position());
    }

    default Soldier move(final Point location) {
        return SoldierData.builder()
                .health(health())
                .strength(strength())
                .race(race())
                .position(location)
                .build();
    }

    default Soldier attack(final Soldier target) {
        return SoldierData.builder()
                .health(target.health() - strength())
                .strength(target.strength())
                .race(target.race())
                .position(target.position())
                .build();
    }

    default Comparator<Soldier> closestWeakestEnemies() {
        return (soldierA, soldierB) -> {
            final int distance = Integer.compare(
                    position().distance(soldierA.position()),
                    position().distance(soldierB.position()));
            if (distance == 0) {
                return Integer.compare(soldierA.health(), soldierB.health());
            }
            return distance;
        };
    }

    enum Race {
        ELF('E'),
        GOBLIN('G');

        private final char symbol;

        public char symbol() {
            return symbol;
        }

        Race(final char symbol) {
            this.symbol = symbol;
        }

        public static Optional<Race> fromSymbol(final char symbol) {
            return Arrays.stream(Race.values())
                    .filter(race -> race.symbol() == symbol)
                    .findFirst();
        }

        public Race enemy() {
            for (final Race race : Race.values()) {
                if (race != this) {
                    return race;
                }
            }
            throw new IllegalStateException("Soldier could not find an opposing race.");
        }
    }
}
