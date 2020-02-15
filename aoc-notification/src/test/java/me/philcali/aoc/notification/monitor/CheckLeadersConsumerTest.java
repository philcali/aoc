package me.philcali.aoc.notification.monitor;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import me.philcali.aoc.notification.AdventOfCode;
import me.philcali.aoc.notification.AdventOfCodeStorage;
import me.philcali.aoc.notification.LeaderboardSessions;
import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.model.LeaderboardSession;
import me.philcali.aoc.notification.model.LeaderboardSessionData;
import me.philcali.aoc.notification.model.ScheduledMessage;

@RunWith(MockitoJUnitRunner.class)
public class CheckLeadersConsumerTest {

    private CheckLeadersConsumer consumer;
    @Mock
    private AdventOfCodeStorage storage;
    @Mock
    private AdventOfCode source;
    private int adventYear;
    @Mock
    private LeaderboardSessions sessions;
    @Mock
    private ScheduledMessage data;

    @Before
    public void setUp() {
        adventYear = 2019;
        consumer = new CheckLeadersConsumer(source, storage, sessions, adventYear);
        doReturn(adventYear).when(data).yearOrCurrent(eq(adventYear));
    }

    @Test
    public void testNoDifference() {
        LeaderboardSession session = new LeaderboardSessionData("boardId", "sessionId");
        doReturn(Stream.of(session)).when(sessions).currentSessions();
        Leaderboard sourceBoard = mock(Leaderboard.class);
        doReturn(Optional.of(sourceBoard)).when(source).leaderboard(eq(2019), eq(session));
        doReturn(Optional.of(sourceBoard)).when(storage).leaderboard(eq(2019), eq(session));
        consumer.accept(data);
        verify(storage, times(0)).updateLeaders(eq(session), eq(sourceBoard));
    }

    @Test
    public void testUpdate() {
        LeaderboardSession session = new LeaderboardSessionData("boardId", "sessionId");
        doReturn(Stream.of(session)).when(sessions).currentSessions();
        Leaderboard sourceBoard = mock(Leaderboard.class);
        doReturn(Optional.of(sourceBoard)).when(source).leaderboard(eq(2019), eq(session));
        doReturn(Optional.empty()).when(storage).leaderboard(eq(2019), eq(session));
        consumer.accept(data);
        verify(storage).updateLeaders(eq(session), eq(sourceBoard));
    }
}
