package me.philcali.aoc.notification.event;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import me.philcali.aoc.notification.leaderboard.Leaderboard;

@RunWith(MockitoJUnitRunner.class)
public class UpdatedLeadersEventTest {
    private UpdatedLeadersEvent event;
    private Date timestamp;
    @Mock
    private Leaderboard leaderboard;
    @Mock
    private Events eventBus;

    @Before
    public void setUp() {
        timestamp = new Date();
        event = UpdatedLeadersEventData.builder()
                .boardId("abc-123")
                .year(2019)
                .leaderboard(leaderboard)
                .timestamp(timestamp)
                .build();
    }

    @Test
    public void testWriteTo() {
        event.writeTo(eventBus);
        verify(eventBus).updatedLeaders(eq(leaderboard));
    }
}
