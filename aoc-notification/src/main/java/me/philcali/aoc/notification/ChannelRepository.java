package me.philcali.aoc.notification;


import java.util.stream.Stream;

import me.philcali.aoc.notification.model.Channel;
import me.philcali.aoc.notification.model.ChannelMetadata;

public interface ChannelRepository {
    Stream<ChannelMetadata> metadata(String type);

    <T extends Channel> T load(ChannelMetadata metadata, Class<T> channelType);
}
