package me.philcali.aoc.notification.module;

import javax.inject.Singleton;

import dagger.Component;
import me.philcali.aoc.notification.AdventOfCode;

@Component(modules = {
        JacksonModule.class,
        HttpModule.class,
        EnvironmentModule.class,
        AdventOfCodeModule.class
})
@Singleton
public interface NotificationComponent {
    AdventOfCode aoc();

}
