package me.philcali.aoc.notification.chime;

import com.fasterxml.jackson.annotation.JsonProperty;

import me.philcali.zero.lombok.annotation.Data;
import me.philcali.zero.lombok.annotation.NonNull;

@Data
public interface ChimeMessage {
    @NonNull
    @JsonProperty("Content")
    String content();
}
