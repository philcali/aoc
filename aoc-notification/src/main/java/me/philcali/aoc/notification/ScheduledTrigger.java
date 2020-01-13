package me.philcali.aoc.notification;

import me.philcali.aoc.notification.model.ScheduledMessageData;
import me.philcali.aoc.notification.module.DaggerNotificationComponent;
import me.philcali.aoc.notification.module.NotificationComponent;

public class ScheduledTrigger {
    private final NotificationComponent component;

    public ScheduledTrigger(final NotificationComponent component) {
        this.component = component;
    }

    public ScheduledTrigger() {
        this(DaggerNotificationComponent.create());
    }

    public void execute(ScheduledMessageData data) {
        System.out.println("Triggered!");
        component.aoc().summaries(data.yearOrCurrent(component.currentYear())).forEach(System.out::println);
    }
}
