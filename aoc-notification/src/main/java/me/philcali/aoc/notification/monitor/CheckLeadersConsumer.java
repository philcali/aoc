package me.philcali.aoc.notification.monitor;

import java.util.function.Consumer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.philcali.aoc.notification.AdventOfCode;
import me.philcali.aoc.notification.AdventOfCodeStorage;
import me.philcali.aoc.notification.LeaderboardSessions;
import me.philcali.aoc.notification.model.ScheduledMessage;
import me.philcali.aoc.notification.module.EnvironmentModule;

@Singleton
public class CheckLeadersConsumer implements Consumer<ScheduledMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckLeadersConsumer.class);
    private final AdventOfCode source;
    private final AdventOfCodeStorage storage;
    private final LeaderboardSessions sessions;
    private final int adventYear;

    @Inject
    public CheckLeadersConsumer(
            final AdventOfCode source,
            final AdventOfCodeStorage storage,
            final LeaderboardSessions sessions,
            @Named(EnvironmentModule.ADVENT_YEAR) final int adventYear) {
        this.source = source;
        this.storage = storage;
        this.sessions = sessions;
        this.adventYear = adventYear;
    }

    @Override
    public void accept(final ScheduledMessage data) {
        final int year = data.yearOrCurrent(adventYear);
        sessions.currentSessions().forEach(session -> {
            LOGGER.info("Found session for {} in {}", session.boardId(), year);
            source.leaderboard(year, session).ifPresent(leaderboard -> {
                LOGGER.info("Found leaders for board {}", session.boardId());
                if (!storage.leaderboard(year, session).filter(leaderboard::equals).isPresent()) {
                    LOGGER.info("Updating leaders for {} in year {}", session.boardId(), year);
                    storage.updateLeaders(session, leaderboard);
                }
            });
        });
    }
}
