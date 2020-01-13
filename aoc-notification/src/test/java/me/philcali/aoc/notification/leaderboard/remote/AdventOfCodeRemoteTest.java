package me.philcali.aoc.notification.leaderboard.remote;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.philcali.aoc.notification.AdventOfCode;
import me.philcali.aoc.notification.exception.AdventOfCodeException;
import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.Problem;
import me.philcali.aoc.notification.leaderboard.ProblemData;
import me.philcali.aoc.notification.leaderboard.ProblemSummary;
import me.philcali.aoc.notification.leaderboard.ProblemSummaryData;
import me.philcali.http.api.HttpMethod;
import me.philcali.http.api.IHttpClient;
import me.philcali.http.api.IRequest;
import me.philcali.http.api.IResponse;

@RunWith(MockitoJUnitRunner.class)
public class AdventOfCodeRemoteTest {

    private AdventOfCode code;
    @Mock
    private IHttpClient client;
    @Mock
    private IRequest request;
    @Mock
    private IResponse response;
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        code = new AdventOfCodeRemote(client, mapper);
        doReturn(response).when(request).respond();
    }

    @Test
    public void testDetails() {
        InputStream stream = getClass().getResourceAsStream("/day.html");
        doReturn(stream).when(response).body();
        doReturn(200).when(response).status();
        doReturn(request).when(client).createRequest(eq(HttpMethod.GET), eq("https://adventofcode.com/2019/day/6"));
        Problem details = code.details(ProblemSummaryData.builder()
                .day(6)
                .year(2019)
                .build());
        Problem expected = ProblemData.builder()
                .title("Day 6: Universal Orbit Map")
                .day(6)
                .year(2019)
                .createdAt(Date.valueOf(LocalDate.of(2019, Month.DECEMBER, 6)))
                .url("https://adventofcode.com/2019/day/6")
                .description("You've landed at the Universal Orbit Map facility on Mercury."
                        + " Because navigation in space often involves transferring between orbits,"
                        + " the orbit maps here are useful for finding efficient routes between, for example,"
                        + " you and Santa. You download a map of the local orbits (your puzzle input).")
                .build();
        assertEquals(expected, details);
    }

    @Test(expected = AdventOfCodeException.class)
    public void testDetailsNotFound() {
        doReturn(404).when(response).status();
        doReturn(request).when(client).createRequest(eq(HttpMethod.GET), eq("https://adventofcode.com/2019/day/6"));
        code.details(ProblemSummaryData.builder().day(6).year(2019).build());
    }

    @Test
    public void testSummariesPreviousYear() {
        InputStream stream = getClass().getResourceAsStream("/2018.html");
        doReturn(stream).when(response).body();
        doReturn(200).when(response).status();
        doReturn(request).when(client).createRequest(eq(HttpMethod.GET), eq("https://adventofcode.com/2018"));
        final List<ProblemSummary> summaries = code.summaries(2018);
        assertEquals(25, summaries.size());
    }

    @Test
    public void testSummariesYetToCome() {
        InputStream stream = getClass().getResourceAsStream("/2019.html");
        doReturn(stream).when(response).body();
        doReturn(200).when(response).status();
        doReturn(request).when(client).createRequest(eq(HttpMethod.GET), eq("https://adventofcode.com/2019"));
        final List<ProblemSummary> summaries = code.summaries(2019);
        assertEquals(0, summaries.size());
    }

    @Test
    public void testSummariesNotFound() {
        doReturn(404).when(response).status();
        doReturn(request).when(client).createRequest(eq(HttpMethod.GET), eq("https://adventofcode.com/2019"));
        final List<ProblemSummary> summaries = code.summaries(2019);
        assertEquals(0, summaries.size());
    }

    @Test
    public void testLeaderboard() {
        InputStream stream = getClass().getResourceAsStream("/leaderboard.json");
        doReturn(200).when(response).status();
        doReturn(stream).when(response).body();
        doReturn(request).when(client).createRequest(eq(HttpMethod.GET), eq("https://adventofcode.com/2019/leaderboard/private/view/12345.json"));
        doReturn(request).when(request).header(eq("Cookie"), eq("sessionId"));
        Leaderboard board = code.leaderboard(2019, "12345", "sessionId");
        assertEquals("2019", board.event());
        assertEquals(36, board.members().entrySet().stream().findFirst().get().getValue().localScore());
    }

    @Test(expected = AdventOfCodeException.class)
    public void testLeaderboardNotFound() {
        doReturn(404).when(response).status();
        doReturn(request).when(client).createRequest(eq(HttpMethod.GET), eq("https://adventofcode.com/2019/leaderboard/private/view/12345.json"));
        doReturn(request).when(request).header(eq("Cookie"), eq("sessionId"));
        code.leaderboard(2019, "12345", "sessionId");
    }

    @Test(expected = AdventOfCodeException.class)
    public void testLeaderboardNotAuthroized() {
        doReturn(401).when(response).status();
        doReturn(request).when(client).createRequest(eq(HttpMethod.GET), eq("https://adventofcode.com/2019/leaderboard/private/view/12345.json"));
        doReturn(request).when(request).header(eq("Cookie"), eq("sessionId"));
        code.leaderboard(2019, "12345", "sessionId");
    }
}
