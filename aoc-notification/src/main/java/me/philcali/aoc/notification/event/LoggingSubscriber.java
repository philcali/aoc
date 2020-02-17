package me.philcali.aoc.notification.event;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.Problem;

public class LoggingSubscriber implements Subscriber {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingSubscriber.class);

    @Inject
    public LoggingSubscriber() {
    }

    @Override
    public void onNewProblem(final Problem problem) {
        LOGGER.info("New problem details were found {}", problem);
    }

    @Override
    public void onUpdatedLeaders(final Leaderboard leaderboard) {
        LOGGER.info("Updated leaders for {}", leaderboard.ownerId());
    }
}
