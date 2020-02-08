package me.philcali.aoc.notification.monitor;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import me.philcali.aoc.notification.AdventOfCode;
import me.philcali.aoc.notification.AdventOfCodeStorage;
import me.philcali.aoc.notification.leaderboard.Problem;
import me.philcali.aoc.notification.leaderboard.ProblemData;
import me.philcali.aoc.notification.leaderboard.ProblemSummary;
import me.philcali.aoc.notification.leaderboard.ProblemSummaryData;
import me.philcali.aoc.notification.model.ScheduledMessage;

@RunWith(MockitoJUnitRunner.class)
public class CheckProblemsConsumerTest {
    private CheckProblemsConsumer monitor;
    private int adventYear;
    @Mock
    private AdventOfCode source;
    @Mock
    private AdventOfCodeStorage storage;
    @Mock
    private ScheduledMessage data;

    @Before
    public void setUp() {
        adventYear = 2019;
        monitor = new CheckProblemsConsumer(source, storage, adventYear);
    }

    @Test
    public void testAcceptUpdate() {
        doReturn(adventYear).when(data).yearOrCurrent(eq(adventYear));
        ProblemSummary summary = ProblemSummaryData.builder()
                .day(1)
                .year(adventYear)
                .build();
        Problem problem = ProblemData.builder()
                .day(1)
                .year(adventYear)
                .build();
        doReturn(Arrays.asList(summary)).when(source).summaries(eq(adventYear));
        doReturn(Optional.empty()).when(storage).details(eq(summary));
        doReturn(Optional.of(problem)).when(source).details(eq(summary));
        monitor.accept(data);
        verify(storage).addProblem(eq(problem));
    }

    @Test
    public void testAcceptNoUpdate() {
        doReturn(adventYear).when(data).yearOrCurrent(eq(adventYear));
        ProblemSummary summary = ProblemSummaryData.builder()
                .day(1)
                .year(adventYear)
                .build();
        Problem problem = ProblemData.builder()
                .day(1)
                .year(adventYear)
                .build();
        doReturn(Arrays.asList(summary)).when(source).summaries(eq(adventYear));
        doReturn(Optional.of(problem)).when(storage).details(eq(summary));
        monitor.accept(data);
        verify(storage, times(0)).addProblem(eq(problem));
    }
}
