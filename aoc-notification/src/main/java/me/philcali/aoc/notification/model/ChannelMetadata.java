package me.philcali.aoc.notification.model;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data @Builder
public interface ChannelMetadata {
    @NonNull
    String name();

    @NonNull
    String id();

    @NonNull
    String type();
}
