package me.philcali.aoc.notification.s3;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.philcali.aoc.notification.AdventOfCode;
import me.philcali.aoc.notification.exception.AdventOfCodeException;
import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.Problem;
import me.philcali.aoc.notification.leaderboard.ProblemSummary;
import me.philcali.aoc.notification.leaderboard.ProblemSummaryData;
import me.philcali.aoc.notification.model.LeaderboardSession;

public class AdventOfCodeS3 implements AdventOfCode {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdventOfCodeS3.class);
    private static final String PROBLEMS_PREFIX = "problems";
    private static final String LEADERBOARDS_PREFIX = "leaderboards";
    private static final String DELIMITER = "/";
    private final String bucket;
    private final AmazonS3 s3;
    private final ObjectMapper mapper;

    public AdventOfCodeS3(
            final String bucket,
            final AmazonS3 s3,
            final ObjectMapper mapper) {
        this.bucket = bucket;
        this.s3 = s3;
        this.mapper = mapper;
    }

    private <T> Optional<T> single(final String key, final Class<T> input) {
        try {
            final S3Object object = s3.getObject(new GetObjectRequest(bucket, key));
            return Optional.of(mapper.readValue(object.getObjectContent(), input));
        } catch (AmazonServiceException ase) {
            if (ase.getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                return Optional.empty();
            }
            LOGGER.error("Failed to pull from {}/{}", bucket, key, ase);
            throw new AdventOfCodeException(ase);
        } catch (IOException ie) {
            LOGGER.error("Failed to deserialize from {}/{}", bucket, key, ie);
            throw new AdventOfCodeException(ie);
        }
    }

    @Override
    public Optional<Problem> details(final ProblemSummary summary) throws AdventOfCodeException {
        final String key = new StringJoiner(DELIMITER)
                .add(PROBLEMS_PREFIX)
                .add(Integer.toString(summary.year()))
                .add(Integer.toString(summary.day()))
                .add("problem.json")
                .toString();
        return single(key, Problem.class);
    }

    @Override
    public Optional<Leaderboard> leaderboard(final int year, final LeaderboardSession session) throws AdventOfCodeException {
        final String key = new StringJoiner(DELIMITER)
                .add(LEADERBOARDS_PREFIX)
                .add(session.boardId())
                .add(Integer.toString(year))
                .add("current.json")
                .toString();
        return single(key, Leaderboard.class);
    }

    @Override
    public List<ProblemSummary> summaries(final int year) throws AdventOfCodeException {
        try {
            final ListObjectsV2Result result = s3.listObjectsV2(new ListObjectsV2Request()
                    .withBucketName(bucket)
                    .withDelimiter(DELIMITER)
                    .withMaxKeys(25)
                    .withPrefix(PROBLEMS_PREFIX + "/" + year));
            return result.getObjectSummaries().stream()
                    .map(summary -> ProblemSummaryData.builder()
                            .year(year)
                            .day(Integer.parseInt(summary.getKey().split(DELIMITER)[3]))
                            .build())
                    .collect(Collectors.toList());
        } catch (AmazonServiceException ase) {
            LOGGER.error("Failed to list problems under {} {}", bucket, year, ase);
            throw new AdventOfCodeException(ase);
        }
    }
}
