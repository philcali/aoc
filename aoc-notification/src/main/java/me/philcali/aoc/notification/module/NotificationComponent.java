package me.philcali.aoc.notification.module;

import javax.inject.Singleton;

import dagger.Component;
import me.philcali.aoc.notification.event.Events;
import me.philcali.aoc.notification.s3.S3EventNotificationRecordParser;

@Component(modules = {
        JacksonModule.class,
        HttpModule.class,
        EnvironmentModule.class,
        StorageModule.class,
        SystemsManagerModule.class,
        EventsModule.class,
        ChannelModule.class
})
@Singleton
public interface NotificationComponent {
    S3EventNotificationRecordParser parser();

    Events eventBus();
}
