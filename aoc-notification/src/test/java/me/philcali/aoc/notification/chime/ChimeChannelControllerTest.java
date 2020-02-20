package me.philcali.aoc.notification.chime;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.philcali.aoc.notification.channel.ChannelController;
import me.philcali.aoc.notification.exception.SendMessageException;
import me.philcali.aoc.notification.leaderboard.Completion;
import me.philcali.aoc.notification.leaderboard.CompletionData;
import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.LeaderboardData;
import me.philcali.aoc.notification.leaderboard.MemberData;
import me.philcali.aoc.notification.leaderboard.Problem;
import me.philcali.http.api.HttpMethod;
import me.philcali.http.api.IHttpClient;
import me.philcali.http.api.IRequest;
import me.philcali.http.api.IResponse;
import me.philcali.http.api.exception.HttpException;

@RunWith(MockitoJUnitRunner.class)
public class ChimeChannelControllerTest {

    private ChannelController<ChimeChannel> controller;
    @Mock
    private IHttpClient http;
    private ObjectMapper mapper;
    @Mock
    private Problem problem;
    @Mock
    private ChimeChannel channel;
    @Mock
    private IRequest request;
    @Mock
    private IResponse response;
    private Leaderboard leaderboard;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        controller = new ChimeChannelController(http, mapper);
        doReturn("https://example.com").when(channel).url();
        doReturn(request).when(http).createRequest(eq(HttpMethod.POST), eq("https://example.com"));

        final long now = System.currentTimeMillis();

        final Map<String, Completion> completions = new HashMap<>();
        completions.put("1", CompletionData.builder()
                .timestamp(now)
                .build());

        leaderboard = LeaderboardData.builder()
                .ownerId("ownerId")
                .event("2019")
                .putMembers("user_1", MemberData.builder()
                        .globalScore(0)
                        .id("user_1")
                        .localScore(4)
                        .lastStartTimestamp(now)
                        .putCompletions("1", completions)
                        .name("User One")
                        .stars(2)
                        .build())
                .putMembers("user_2", MemberData.builder()
                        .globalScore(0)
                        .id("user_2")
                        .localScore(8)
                        .lastStartTimestamp(now)
                        .putCompletions("1", completions)
                        .name("User Two")
                        .stars(4)
                        .build())
                .build();
    }

    @Test
    public void testMetadata() {
        assertEquals("chime", controller.metadata());
    }

    @Test
    public void testChannelType() {
        assertEquals(ChimeChannel.class, controller.channelType());
    }

    @Test
    public void testSendNewProblem() throws JsonProcessingException {
        doReturn("Title").when(problem).title();
        doReturn("Description").when(problem).description();
        doReturn(request).when(request).body(eq(mapper.writeValueAsString(new ChimeMessageData("@Present New Problem! Title\n\nDescription"))));
        doReturn(request).when(request).header(eq("Content-Type"), eq("application/json"));
        doReturn(response).when(request).respond();
        doReturn(new ByteArrayInputStream(new String("{}").getBytes(StandardCharsets.UTF_8))).when(response).body();
        controller.sendNewProblem(channel, problem);
    }

    @Test(expected = SendMessageException.class)
    public void testSendNewProblemHttpException() throws JsonProcessingException {
        doReturn("Title").when(problem).title();
        doReturn("Description").when(problem).description();
        doReturn(request).when(request).body(eq(mapper.writeValueAsString(new ChimeMessageData("@Present New Problem! Title\n\nDescription"))));
        doReturn(request).when(request).header(eq("Content-Type"), eq("application/json"));
        doThrow(HttpException.class).when(request).respond();
        controller.sendNewProblem(channel, problem);
    }

    @Test
    public void testSendUpdatedLeaders() throws JsonProcessingException {
        doReturn(request).when(request).body(eq(mapper.writeValueAsString(new ChimeMessageData("/md\n|__User__|__Stars__|__Score__|\n|---|---|---|\n|User Two|4|8|\n|User One|2|4|"))));
        doReturn(request).when(request).header(eq("Content-Type"), eq("application/json"));
        doReturn(response).when(request).respond();
        doReturn(new ByteArrayInputStream(new String("{}").getBytes(StandardCharsets.UTF_8))).when(response).body();
        controller.sendUpdatedLeaders(channel, leaderboard);
    }
}
