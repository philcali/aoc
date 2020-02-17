package me.philcali.aoc.notification.event;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import me.philcali.aoc.notification.leaderboard.Problem;

@RunWith(MockitoJUnitRunner.class)
public class NewProblemEventTest {
    private NewProblemEvent event;
    @Mock
    private Problem problem;
    @Mock
    private Events eventBus;

    @Before
    public void setUp() {
        event = NewProblemEventData.builder()
                .timestamp(new Date())
                .problem(problem)
                .build();
    }

    @Test
    public void testWriteTo() {
        event.writeTo(eventBus);
        verify(eventBus).newProblem(eq(problem));
    }
}
