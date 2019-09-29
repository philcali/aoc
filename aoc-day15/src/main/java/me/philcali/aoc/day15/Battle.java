package me.philcali.aoc.day15;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

import me.philcali.aoc.common.geometry.Point;
import me.philcali.aoc.common.geometry.PointData;
import me.philcali.aoc.day15.Soldier.Race;
import me.philcali.aoc.day15.Terrain.Type;
import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.ConcreteType;
import me.philcali.zero.lombok.annotation.ConcreteTypes;
import me.philcali.zero.lombok.annotation.NonNull;
import me.philcali.zero.lombok.annotation.ToString;

@Builder @ToString
@ConcreteTypes({
    @ConcreteType(contract = Map.class, implementation = TreeMap.class),
    @ConcreteType(contract = Set.class, implementation = HashSet.class)
})
public interface Battle {
    @NonNull
    Map<Point, Terrain> field();
    @NonNull
    Map<Point, Soldier> soldiers();

    default boolean isComplete() {
        return Arrays.stream(Race.values())
                .anyMatch(race -> remainingEnemySoldiers(race).isEmpty());
    }


    default Set<Soldier> remainingEnemySoldiers(final Race race) {
        return soldiers().values().stream()
                .filter(soldier -> soldier.race().equals(race))
                .collect(Collectors.toSet());
    }

    default boolean isObstructed(final Point point) {
        return soldiers().containsKey(point)
                || !field().containsKey(point)
                || field().get(point).type().isObstruction();
    }

    /**
     * Modified A* algo... stupid
     *
     */
    default TracedPath shortestPath(final Point from, final Point to) {
        final Map<Point, Point> cameFrom = new HashMap<>();
        final Map<Point, Integer> totalDistances = new HashMap<>();
        totalDistances.put(from, 1);
        final PriorityQueue<Node> openSet = new PriorityQueue<>();
        openSet.offer(new NodeData(1, from));
        final Set<Point> closedSet = new HashSet<>();
        while (!openSet.isEmpty()) {
            final Node current = openSet.poll();
            if (current.point().equals(to)) {
                final List<Point> linkedList = new LinkedList<>();
                linkedList.add(current.point());
                Point point = current.point();
                while (cameFrom.containsKey(point)) {
                    point = cameFrom.get(point);
                    linkedList.add(0, point);
                }
                return TracedPathData.builder()
                        .routesToTarget(true)
                        .line(linkedList)
                        .steps(linkedList.size())
                        .build();
            }
            closedSet.add(current.point());
            for (final Point neighbor : current.point().radial(1)) {
                if (isObstructed(neighbor) || closedSet.contains(neighbor)) {
                    continue;
                }
                int tentativeScore = current.steps() + 1;
                if (tentativeScore < totalDistances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current.point());
                    totalDistances.put(neighbor, tentativeScore);
                    final Node neighborNode = new NodeData(tentativeScore, neighbor);
                    if (!openSet.contains(neighborNode)) {
                        openSet.offer(neighborNode);
                    }
                }
            }
        }
        return TracedPathData.builder()
                .routesToTarget(false)
                .build();
    }

    default void round() {
        final Set<String> deaths = new HashSet<>();
        for (final Soldier soldier : soldiers().values().stream().collect(Collectors.toList())) {
            if (deaths.contains(soldier.uuid())) {
                continue;
            }
            final Set<Soldier> targets = remainingEnemySoldiers(soldier.race().enemy());
            if (targets.isEmpty()) {
                throw new BattleFinishedEarlyException("The war ended early!");
            }
            // Determine if movement is necessary
            final PriorityQueue<Soldier> nearSoldiers = new PriorityQueue<>(soldier.closestWeakestEnemies());
            final PriorityQueue<Soldier> distantSoldiers = new PriorityQueue<>();
            targets.stream().forEach(target -> {
                // first test if the the two are next to each other
                final int distance = soldier.position().distance(target.position());
                if (distance <= 1) {
                    nearSoldiers.offer(target);
                } else {
                    distantSoldiers.offer(target);
                }
            });
            if (nearSoldiers.isEmpty()) {
                // move ... these extra filtering are required for the problem not optimization
                final Optional<TracedPath> shortestReachablePath = distantSoldiers.stream()
                        .flatMap(target -> target.position().radial(1).stream()
                                .filter(point -> !isObstructed(point))
                                .flatMap(adjacent -> soldier.position().radial(1).stream()
                                        .filter(point -> !isObstructed(point))
                                        .map(sa -> shortestPath(sa, adjacent))))
                        .filter(TracedPath::routesToTarget)
                        .sorted()
                        .findFirst();
                shortestReachablePath.ifPresent(path -> {
                    final Soldier newSoldier = soldier.move(path.line().get(0));
                    soldiers().remove(soldier.position());
                    soldiers().put(newSoldier.position(), newSoldier);
                    // Determine if the new position is next to any enemy
                    remainingEnemySoldiers(soldier.race().enemy()).stream()
                    .filter(enemy -> enemy.position().radial(1).contains(newSoldier.position()))
                    .forEach(nearSoldiers::add);
                });
            }
            // ATTACK!
            if (!nearSoldiers.isEmpty()) {
                final Soldier originalTarget = nearSoldiers.poll();
                final Soldier target = soldier.attack(originalTarget);
                soldiers().remove(target.position());
                if (!target.isDead()) {
                    soldiers().put(target.position(), target);
                } else {
                    deaths.add(originalTarget.uuid());
                }
            }
        };
    }

    static Battle create(final List<String> input) {
        final Map<Point, Terrain> battleField = new TreeMap<>();
        final Map<Point, Soldier> soldiers = new TreeMap<>();
        int y = 0;
        for (final String line : input) {
            int x = 0;
            for (final char tile : line.toCharArray()) {
                final Point point = new PointData(x, y);
                Race.fromSymbol(tile).ifPresent(race -> {
                    soldiers.put(point, SoldierData.builder()
                            .uuid(UUID.randomUUID().toString())
                            .position(point)
                            .race(race)
                            .build());
                    battleField.put(point, new TerrainData(point, Type.OPEN_SPACE));
                });
                Type.fromSymbol(tile).ifPresent(terrain -> {
                    battleField.put(point, new TerrainData(point, terrain));
                });
                x++;
            }
            y++;
        }
        return BattleData.builder()
                .field(battleField)
                .soldiers(soldiers)
                .build();
    }
}
