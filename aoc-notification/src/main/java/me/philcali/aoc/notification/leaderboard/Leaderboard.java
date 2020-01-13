package me.philcali.aoc.notification.leaderboard;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.Data;

@Builder @Data
@JsonDeserialize(builder = LeaderboardData.Builder.class)
public interface Leaderboard {
    @JsonProperty("event")
    String event();

    @JsonProperty("owner_id")
    String ownerId();

    @JsonProperty("members")
    Map<String, Member> members();
}
