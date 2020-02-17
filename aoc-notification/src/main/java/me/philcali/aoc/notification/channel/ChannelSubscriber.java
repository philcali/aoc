package me.philcali.aoc.notification.channel;

import java.util.List;
import java.util.function.BiConsumer;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.philcali.aoc.notification.event.Subscriber;
import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.Problem;

@Singleton
public class ChannelSubscriber implements Subscriber {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelSubscriber.class);
    private final ChannelRepository channels;
    private final List<ChannelController<?>> controllers;

    @Inject
    public ChannelSubscriber(
            final ChannelRepository channels,
            final List<ChannelController<?>> controllers) {
        this.channels = channels;
        this.controllers = controllers;
    }

    @SuppressWarnings({ "rawtypes" })
    private void controllers(final BiConsumer<ChannelController, Channel> onChannel) {
        // todo: consider this maybe? ServiceLoader.load(ChannelController.class)
        controllers.stream().forEach(controller -> {
            channels.metadata(controller.metadata()).forEach(metadata -> {
                LOGGER.info("Found metadata {} for {}", metadata, controller.channelType());
                final Channel channel = channels.load(metadata, controller.channelType());
                onChannel.accept(controller, channel);
            });
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onNewProblem(final Problem problem) {
        controllers((controller, channel) -> controller.sendNewProblem(channel, problem));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onUpdatedLeaders(final Leaderboard leaderboard) {
        controllers((controller, channel) -> controller.sendUpdatedLeaders(channel, leaderboard));
    }
}
