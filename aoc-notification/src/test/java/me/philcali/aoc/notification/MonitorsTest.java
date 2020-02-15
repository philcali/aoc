package me.philcali.aoc.notification;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import me.philcali.aoc.notification.model.ScheduledMessageData;
import me.philcali.aoc.notification.module.SchedulingComponent;
import me.philcali.aoc.notification.monitor.CheckLeadersConsumer;
import me.philcali.aoc.notification.monitor.CheckProblemsConsumer;

@RunWith(MockitoJUnitRunner.class)
public class MonitorsTest {

    private Monitors monitors;
    @Mock
    private SchedulingComponent component;
    @Mock
    private ScheduledMessageData data;
    @Mock
    private CheckProblemsConsumer checkProblems;
    @Mock
    private CheckLeadersConsumer checkLeaders;

    @Before
    public void setUp() {
        monitors = new Monitors(component);
    }

    @Test
    public void testCheckProblems() {
        doReturn(checkProblems).when(component).checkProblems();
        monitors.checkProblems(data);
        verify(checkProblems).accept(eq(data));
    }

    @Test
    public void testCheckLeaders() {
        doReturn(checkLeaders).when(component).checkLeaders();
        monitors.checkLeaders(data);
        verify(checkLeaders).accept(eq(data));
    }
}
