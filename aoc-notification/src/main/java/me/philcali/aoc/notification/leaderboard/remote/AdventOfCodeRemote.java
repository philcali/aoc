package me.philcali.aoc.notification.leaderboard.remote;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.philcali.aoc.notification.AdventOfCode;
import me.philcali.aoc.notification.exception.AdventOfCodeException;
import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.Problem;
import me.philcali.aoc.notification.leaderboard.ProblemData;
import me.philcali.aoc.notification.leaderboard.ProblemSummary;
import me.philcali.aoc.notification.leaderboard.ProblemSummaryData;
import me.philcali.aoc.notification.model.LeaderboardSession;
import me.philcali.http.api.HttpMethod;
import me.philcali.http.api.IHttpClient;
import me.philcali.http.api.IRequest;
import me.philcali.http.api.IResponse;

public class AdventOfCodeRemote implements AdventOfCode {
    private static final String BASE_URL = "https://adventofcode.com";
    private IHttpClient client;
    private ObjectMapper mapper;

    @FunctionalInterface
    private static interface StreamFunction<O> {
        O apply(InputStream input) throws IOException;
    }


    @Inject
    public AdventOfCodeRemote(
            final IHttpClient client,
            final ObjectMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    private <T> Optional<T> handleDocument(final String baseUrl, final Function<Document, T> thunk) {
        return handleRequest(
                () -> client.createRequest(HttpMethod.GET, baseUrl),
                stream -> Jsoup.parse(stream, "UTF-8", BASE_URL)).map(thunk);
    }

    private <O> Optional<O> handleRequest(final Supplier<IRequest> createRequest, final StreamFunction<O> thunk) {
        final IRequest request = createRequest.get();
        final IResponse response = request.respond();
        try (final InputStream stream = response.body()) {
            if (response.status() == 200) {
                return Optional.of(thunk.apply(stream));
            } else if (response.status() == 404) {
                return Optional.empty();
            } else {
                throw new AdventOfCodeException("Unhandled response: " + response.toString());
            }
        } catch (IOException ie) {
            throw new AdventOfCodeException(ie);
        }
    }

    @Override
    public Optional<Problem> details(final ProblemSummary summary) {
        final String baseUrl = new StringJoiner("/")
                .add(BASE_URL)
                .add(Integer.toString(summary.year()))
                .add("day")
                .add(Integer.toString(summary.day()))
                .toString();
        return handleDocument(baseUrl, document -> {
            return ProblemData.builder()
                    .day(summary.day())
                    .year(summary.year())
                    .createdAt(Date.valueOf(LocalDate.of(summary.year(), Month.DECEMBER, summary.day())))
                    .title(document.selectFirst("article.day-desc > h2").text().replace("-", "").trim())
                    .description(document.selectFirst("article.day-desc > p").text())
                    .url(baseUrl)
                    .build();
        });
    }

    @Override
    public Optional<Leaderboard> leaderboard(final int year, final LeaderboardSession session) {
        final String baseUrl = new StringJoiner("/")
                .add(BASE_URL)
                .add(Integer.toString(year))
                .add("leaderboard")
                .add("private")
                .add("view")
                .add(session.boardId() + ".json")
                .toString();
        return handleRequest(
                () -> client.createRequest(HttpMethod.GET, baseUrl).header("Cookie", session.sessionId()),
                stream -> mapper.readValue(stream, Leaderboard.class));
    }

    @Override
    public List<ProblemSummary> summaries(final int year) {
        final String baseUrl = BASE_URL + "/" + year;
        return handleDocument(baseUrl, document -> {
            return document.select("main pre.calendar a").stream()
                    .map(link -> link.attr("class").replace("calendar-day", ""))
                    .map(day -> ProblemSummaryData.builder()
                            .year(year)
                            .day(Integer.parseInt(day))
                            .build())
                    .sorted()
                    .collect(Collectors.toList());
        }).orElseGet(Collections::emptyList);
    }
}
