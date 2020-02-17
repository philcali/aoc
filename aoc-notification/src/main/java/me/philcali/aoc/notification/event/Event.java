package me.philcali.aoc.notification.event;

import java.util.Date;

public interface Event {
    Date timestamp();

    void writeTo(Events eventBus);
}
