package me.philcali.aoc.notification;

import com.amazonaws.services.lambda.runtime.events.S3Event;

import me.philcali.aoc.notification.module.DaggerNotificationComponent;
import me.philcali.aoc.notification.module.NotificationComponent;

public class Notifications {
    private final NotificationComponent component;

    public Notifications(final NotificationComponent component) {
        this.component = component;
    }

    public Notifications() {
        this(DaggerNotificationComponent.create());
    }

    public void updateChannels(final S3Event event) {
        event.getRecords().stream()
                .map(component.parser()::parse)
                .forEach(systemEvent -> systemEvent.ifPresent(system -> system.writeTo(component.eventBus())));
        System.out.println("Triggered");
    }
}
