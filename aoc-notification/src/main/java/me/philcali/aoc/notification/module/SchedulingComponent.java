package me.philcali.aoc.notification.module;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        JacksonModule.class,
        HttpModule.class,
        EnvironmentModule.class,
        AdventOfCodeModule.class
})
@Singleton
public interface SchedulingComponent {

}
