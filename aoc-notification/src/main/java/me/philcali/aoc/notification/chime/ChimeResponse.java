package me.philcali.aoc.notification.chime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import me.philcali.zero.lombok.annotation.Builder;
import me.philcali.zero.lombok.annotation.EqualsAndHashCode;
import me.philcali.zero.lombok.annotation.ToString;

@Builder @EqualsAndHashCode @ToString
@JsonDeserialize(builder = ChimeResponseData.Builder.class)
public interface ChimeResponse {
    @JsonProperty("RoomId")
    String roomId();

    @JsonProperty("MessageId")
    String messageId();
}
