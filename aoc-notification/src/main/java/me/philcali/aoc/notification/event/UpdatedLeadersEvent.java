package me.philcali.aoc.notification.event;

import me.philcali.aoc.notification.Events;
import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data @Builder
public interface UpdatedLeadersEvent extends Event {
    @NonNull
    Leaderboard leaderboard();

    String boardId();

    int year();

    @Override
    default void writeTo(final Events eventBus) {
        eventBus.updatedLeaders(leaderboard());
    }
}
