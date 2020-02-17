package me.philcali.aoc.notification;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.events.S3Event;

import me.philcali.aoc.notification.event.Event;
import me.philcali.aoc.notification.module.DaggerNotificationComponent;
import me.philcali.aoc.notification.module.NotificationComponent;

public class Notifications {
    private static final Logger LOGGER = LoggerFactory.getLogger(Notifications.class);
    private final NotificationComponent component;

    public Notifications(final NotificationComponent component) {
        this.component = component;
    }

    public Notifications() {
        this(DaggerNotificationComponent.create());
    }

    private Consumer<Event> logEvent() {
        return systemEvent -> LOGGER.info("Parsed an event {}", systemEvent);
    }

    public void updateChannels(final S3Event event) {
        event.getRecords().stream()
                .map(component.parser()::parse)
                .forEach(systemEvent -> systemEvent
                        .ifPresent(logEvent().andThen(system -> system.writeTo(component.eventBus()))));
        LOGGER.info("Finished the event notifications");
    }
}
