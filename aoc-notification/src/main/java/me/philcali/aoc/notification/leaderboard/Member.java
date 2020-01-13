package me.philcali.aoc.notification.leaderboard;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;

@Builder @Data
@JsonDeserialize(builder = MemberData.Builder.class)
public interface Member {
    @JsonProperty("id")
    String id();

    @JsonProperty("name")
    String name();

    @JsonProperty("stars")
    int stars();

    @JsonProperty("global_score")
    int globalScore();

    @JsonProperty("local_score")
    int localScore();

    @JsonProperty("completion_day_level")
    Map<String, Map<String, Completion>> completions();

    @JsonProperty("last_star_ts")
    long lastStartTimestamp();
}
