package me.philcali.aoc.notification.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.philcali.aoc.notification.Events;
import me.philcali.aoc.notification.event.EventBusData;
import me.philcali.aoc.notification.event.LoggingSubscriber;

@Module
public class EventsModule {
    @Provides
    @Singleton
    static Events providesEventBus(final LoggingSubscriber logger) {
        return EventBusData.builder()
                .addSubscribers(logger)
                .build();
    }
}
