package me.philcali.aoc.notification.event;

import java.util.List;

import me.philcali.aoc.notification.Events;
import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.Problem;
import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.NonNull;
import me.philcali.zero.lombok.annotation.ToString;

@Builder @ToString
public interface EventBus extends Events {
    @NonNull
    List<Subscriber> subscribers();

    @Override
    default void newProblem(final Problem problem) {
        subscribers().forEach(subscriber -> subscriber.onNewProblem(problem));
    }

    @Override
    default void updatedLeaders(final Leaderboard leaderboard) {
        subscribers().forEach(subscriber -> subscriber.onUpdatedLeaders(leaderboard));
    }
}
