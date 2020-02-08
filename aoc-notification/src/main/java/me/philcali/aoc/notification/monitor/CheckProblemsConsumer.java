package me.philcali.aoc.notification.monitor;

import java.util.function.Consumer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.philcali.aoc.notification.AdventOfCode;
import me.philcali.aoc.notification.AdventOfCodeStorage;
import me.philcali.aoc.notification.model.ScheduledMessage;
import me.philcali.aoc.notification.module.EnvironmentModule;

@Singleton
public class CheckProblemsConsumer implements Consumer<ScheduledMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckProblemsConsumer.class);
    private final int adventYear;
    private final AdventOfCode source;
    private final AdventOfCodeStorage storage;

    @Inject
    public CheckProblemsConsumer(
            final AdventOfCode source,
            final AdventOfCodeStorage storage,
            @Named(EnvironmentModule.ADVENT_YEAR) final int adventYear) {
        this.adventYear = adventYear;
        this.source = source;
        this.storage = storage;
    }

    @Override
    public void accept(final ScheduledMessage data) {
        source.summaries(data.yearOrCurrent(adventYear)).forEach(summary -> {
            LOGGER.info("Found the following problem from AoC: {}", summary);
            if (!storage.details(summary).isPresent()) {
                LOGGER.info("Adding the problem details for {}", summary);
                source.details(summary).ifPresent(storage::addProblem);
            }
        });
    }
}
