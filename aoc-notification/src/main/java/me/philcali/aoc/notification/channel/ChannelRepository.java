package me.philcali.aoc.notification.channel;


import java.util.stream.Stream;

public interface ChannelRepository {
    Stream<ChannelMetadata> metadata(String type);

    <T extends Channel> T load(ChannelMetadata metadata, Class<T> channelType);
}
