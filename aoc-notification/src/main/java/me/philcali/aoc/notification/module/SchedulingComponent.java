package me.philcali.aoc.notification.module;

import javax.inject.Singleton;

import dagger.Component;
import me.philcali.aoc.notification.monitor.CheckProblemsConsumer;

@Component(modules = {
        JacksonModule.class,
        HttpModule.class,
        EnvironmentModule.class,
        AdventOfCodeModule.class,
        StorageModule.class,
        SystemsManagerModule.class
})
@Singleton
public interface SchedulingComponent {
    CheckProblemsConsumer checkProblems();
}
