package me.philcali.aoc.notification.event;

import me.philcali.aoc.notification.Events;
import me.philcali.aoc.notification.leaderboard.Problem;
import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data @Builder
public interface NewProblemEvent extends Event {
    @NonNull
    Problem problem();

    @Override
    default void writeTo(final Events eventBus) {
        eventBus.newProblem(problem());
    }
}
