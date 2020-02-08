package me.philcali.aoc.notification;

import java.util.stream.Stream;

import me.philcali.aoc.notification.model.LeaderboardSession;

public interface LeaderboardSessions {
    Stream<LeaderboardSession> currentSessions();
}
