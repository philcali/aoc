package me.philcali.aoc.notification.channel;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.Problem;

@RunWith(MockitoJUnitRunner.class)
public class ChannelSubscriberTest {
    private ChannelSubscriber subscriber;
    @Mock
    private ChannelRepository channels;
    private List<ChannelController<?>> controllers;
    @Mock
    ChannelController<Channel> controller;
    @Mock
    private Problem problem;
    @Mock
    private Leaderboard leaderboard;
    @Mock
    private Channel channel;
    @Mock
    private ChannelMetadata metadata;

    @Before
    public void setUp() {
        controllers = Arrays.asList(controller);
        subscriber = new ChannelSubscriber(channels, controllers);
        doReturn("chime").when(controller).metadata();
        doReturn(Channel.class).when(controller).channelType();
    }

    @Test
    public void testOnNewProblem() {
        doReturn(Stream.of(metadata)).when(channels).metadata(eq("chime"));
        doReturn(channel).when(channels).load(eq(metadata), eq(Channel.class));
        subscriber.onNewProblem(problem);
        verify(controller).sendNewProblem(eq(channel), eq(problem));
    }

    @Test
    public void testOnUpdatedLeaders() {
        subscriber.onUpdatedLeaders(leaderboard);
    }
}
