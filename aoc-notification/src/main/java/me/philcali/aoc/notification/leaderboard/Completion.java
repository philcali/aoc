package me.philcali.aoc.notification.leaderboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;

@Builder @Data
@JsonDeserialize(builder = CompletionData.Builder.class)
public interface Completion {
    @JsonProperty("get_star_ts")
    long timestamp();
}
