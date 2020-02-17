package me.philcali.aoc.notification.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.philcali.aoc.notification.ChannelRepository;
import me.philcali.aoc.notification.ssm.ChannelRepositoryParameters;

@Module
public class ChannelModule {
    @Provides
    @Singleton
    static ChannelRepository providesChannelRepository(final ChannelRepositoryParameters repository) {
        return repository;
    }
}
