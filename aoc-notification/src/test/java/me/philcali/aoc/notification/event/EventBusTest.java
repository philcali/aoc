package me.philcali.aoc.notification.event;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.Problem;

@RunWith(MockitoJUnitRunner.class)
public class EventBusTest {
    private EventBus eventBus;
    @Mock
    private Subscriber subscriber;
    @Mock
    private Problem problem;
    @Mock
    private Leaderboard leaderboard;

    @Before
    public void setUp() {
        eventBus = EventBusData.builder()
                .addSubscribers(new LoggingSubscriber())
                .addSubscribers(subscriber)
                .build();
    }

    @Test
    public void testOnNewProblem() {
        eventBus.newProblem(problem);
        verify(subscriber).onNewProblem(eq(problem));
    }

    @Test
    public void testOnUpdatedLeaders() {
        eventBus.updatedLeaders(leaderboard);
        verify(subscriber).onUpdatedLeaders(eq(leaderboard));
    }
}
