package me.philcali.aoc.notification;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.philcali.aoc.notification.model.ScheduledMessageData;
import me.philcali.aoc.notification.module.DaggerSchedulingComponent;
import me.philcali.aoc.notification.module.SchedulingComponent;

public class Monitors {
    private static final Logger LOGGER = LoggerFactory.getLogger(Monitors.class);
    private final SchedulingComponent component;

    public Monitors(final SchedulingComponent component) {
        this.component = component;
    }

    public Monitors() {
        this(DaggerSchedulingComponent.create());
    }

    public void checkProblems(final ScheduledMessageData data) {
        component.checkProblems().accept(data);
        LOGGER.info("Finished checking the problem set");
    }

    public void checkLeaders(final ScheduledMessageData data) {
        component.checkLeaders().accept(data);
        LOGGER.info("Finished checking the current leaders");
    }
}
