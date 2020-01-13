package me.philcali.aoc.notification.chime;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChimeResponseTest {
    private ObjectMapper mapper;
    private ChimeResponse response;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        response = ChimeResponseData.builder()
                .messageId("messageId")
                .roomId("roomId")
                .build();
    }

    @Test
    public void testMarshalling() throws JsonProcessingException {
        String json = mapper.writeValueAsString(response);
        assertEquals("{\"MessageId\":\"messageId\",\"RoomId\":\"roomId\"}", json);
        assertEquals(response, mapper.readValue(json, ChimeResponse.class));
    }
}
