package me.philcali.aoc.notification.leaderboard;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.EqualsAndHashCode;
import me.philcali.zero.lombok.annotation.ToString;

@Builder @EqualsAndHashCode @ToString
@JsonDeserialize(builder = ProblemData.Builder.class)
public interface Problem extends ProblemSummary {
    @JsonProperty("url")
    String url();
    @JsonProperty("title")
    String title();
    @JsonProperty("description")
    String description();
    @JsonProperty("createdAt")
    Date createdAt();
}
