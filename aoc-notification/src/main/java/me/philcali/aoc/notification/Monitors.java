package me.philcali.aoc.notification;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.philcali.aoc.notification.model.ScheduledMessageData;
import me.philcali.aoc.notification.module.DaggerNotificationComponent;
import me.philcali.aoc.notification.module.NotificationComponent;

public class Monitors {
    private static final Logger LOGGER = LoggerFactory.getLogger(Monitors.class);
    private final NotificationComponent component;

    public Monitors(final NotificationComponent component) {
        this.component = component;
    }

    public Monitors() {
        this(DaggerNotificationComponent.create());
    }

    public void checkProblems(final ScheduledMessageData data) {
        LOGGER.info("Triggered!");
    }

    public void checkLeaders(final ScheduledMessageData data) {
        LOGGER.info("Leaderboard check!");
    }
}
