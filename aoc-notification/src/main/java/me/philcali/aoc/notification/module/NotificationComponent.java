package me.philcali.aoc.notification.module;

import javax.inject.Named;
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

    @Named(EnvironmentModule.ADVENT_YEAR)
    int currentYear();

    @Named(EnvironmentModule.ADVENT_DAY)
    int currentDay();
}
