package me.philcali.aoc.notification.module;

import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.philcali.aoc.notification.channel.ChannelController;
import me.philcali.aoc.notification.channel.ChannelRepository;
import me.philcali.aoc.notification.chime.ChimeChannelController;
import me.philcali.aoc.notification.ssm.ChannelRepositoryParameters;

@Module
public class ChannelModule {
    @Provides
    @Singleton
    static ChannelRepository providesChannelRepository(final ChannelRepositoryParameters repository) {
        return repository;
    }

    @Provides
    @Singleton
    static List<ChannelController<?>> providesChannelControllers(final ChimeChannelController chime) {
        // todo: consider simplifying by ServiceLoader
        return Arrays.asList(chime);
    }
}
