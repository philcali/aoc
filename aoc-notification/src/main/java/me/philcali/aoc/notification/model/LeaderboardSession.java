package me.philcali.aoc.notification.model;

import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface LeaderboardSession {
    @NonNull
    String sessionId();
    @NonNull
    String boardId();
}
