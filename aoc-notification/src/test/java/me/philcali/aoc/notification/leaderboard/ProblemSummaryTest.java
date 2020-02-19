package me.philcali.aoc.notification.leaderboard;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ProblemSummaryTest {
    private List<ProblemSummary> summaries;

    @Before
    public void setUp() {
        summaries = new ArrayList<>();
    }

    @Test
    public void testCompareTo() {
        summaries.add(ProblemSummaryData.builder().day(6).year(2020).build());
        summaries.add(ProblemSummaryData.builder().day(6).year(2019).build());
        summaries.add(ProblemSummaryData.builder().day(5).year(2019).build());
        List<ProblemSummary> expected = Arrays.asList(summaries.get(2), summaries.get(1), summaries.get(0));
        Collections.sort(summaries);
        assertEquals(expected, summaries);
    }
}
