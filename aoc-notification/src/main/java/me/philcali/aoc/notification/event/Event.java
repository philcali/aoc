package me.philcali.aoc.notification.event;

import java.util.Date;

import me.philcali.aoc.notification.Events;

public interface Event {
    Date timestamp();

    void writeTo(Events eventBus);
}
