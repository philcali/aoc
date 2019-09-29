package me.philcali.aoc.day15;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.auto.service.AutoService;

import me.philcali.aoc.common.AnnotatedDailyEvent;
import me.philcali.aoc.common.DailyEvent;
import me.philcali.aoc.common.DailyInputEvent;
import me.philcali.aoc.common.Day;
import me.philcali.aoc.common.Description;
import me.philcali.aoc.common.Problem;
import me.philcali.aoc.common.Year;

@Problem(1) @Day(15) @Year(2018)
@Description("Beverage Bandits")
@AutoService(DailyEvent.class)
public class ProblemOne implements AnnotatedDailyEvent, DailyInputEvent {
    @Override
    public void run() {
        final Battle battle = Battle.create(readLines());
        int rounds = 0;
        try {
            do {
                battle.round();
                rounds++;
            } while (!battle.isComplete());
        } catch (BattleFinishedEarlyException e) {
            System.out.println(e.getMessage());
        }
        render(battle, rounds);
    }

    private void render(final Battle battle, final int rounds) {
        System.out.println("After " + rounds + " rounds:");
        final AtomicInteger lastY = new AtomicInteger();
        battle.field().keySet().stream().forEach(point -> {
            if (point.y() != lastY.get()) {
                lastY.set(point.y());
                System.out.print("\n");
            }
            System.out.print(Optional.ofNullable(battle.soldiers().get(point))
                    .map(soldier -> soldier.race().symbol())
                    .orElseGet(() -> battle.field().get(point).type().symbol()));
        });
        System.out.println();
        System.out.println("Soldiers outcome: " + (rounds * battle.soldiers().values().stream()
                .map(Soldier::health)
                .reduce(Integer::sum)
                .orElse(0)));
    }
}
