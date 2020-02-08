package me.philcali.aoc.notification.s3;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.philcali.aoc.notification.AdventOfCodeStorage;
import me.philcali.aoc.notification.exception.AdventOfCodeException;
import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.Problem;
import me.philcali.aoc.notification.leaderboard.ProblemSummary;
import me.philcali.aoc.notification.leaderboard.ProblemSummaryData;
import me.philcali.aoc.notification.model.LeaderboardSession;

public class AdventOfCodeS3 implements AdventOfCodeStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdventOfCodeS3.class);
    private static final String PROBLEMS_PREFIX = "problems";
    private static final String LEADERBOARDS_PREFIX = "leaderboards";
    private static final String DELIMITER = "/";
    private static final String PROBLEM_FILE = "problem.json";
    private static final String LEADERBOAD_FILE = "current.json";
    private static final int MAX_ITEMS = 25;
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

    private <T> Optional<T> readSingle(final String key, final Class<T> input) {
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

    private void putSingle(final String key, final Object object) {
        try {
            final byte[] jsonBytes = mapper.writeValueAsBytes(object);
            try (final InputStream input = new ByteArrayInputStream(jsonBytes)) {
                final ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(jsonBytes.length);
                metadata.setContentType("application/json");
                final PutObjectResult result = s3.putObject(new PutObjectRequest(bucket, key, input, metadata));
                LOGGER.info("Successfully put {}: {}", key, result.getETag());
            }
        } catch (AmazonS3Exception ase) {
            LOGGER.error("Failed to put object at {}/{}", bucket, key, ase);
            throw new AdventOfCodeException(ase);
        } catch (IOException e) {
            LOGGER.error("Failed to serialize {}", object, e);
            throw new AdventOfCodeException(e);
        }
    }

    private String problemKey(final ProblemSummary summary) {
        return new StringJoiner(DELIMITER)
                .add(PROBLEMS_PREFIX)
                .add(Integer.toString(summary.year()))
                .add(String.format("%02d", summary.day()))
                .add(PROBLEM_FILE)
                .toString();
    }

    private String leaderboardKey(final String ... args) {
        return Arrays.stream(args)
                .reduce(new StringJoiner(DELIMITER).add(LEADERBOARDS_PREFIX),
                        (left, right) -> left.add(right),
                        (left, right) -> left)
                .toString();
    }

    @Override
    public Optional<Problem> details(final ProblemSummary summary) throws AdventOfCodeException {
        return readSingle(problemKey(summary), Problem.class);
    }

    @Override
    public Optional<Leaderboard> leaderboard(final int year, final LeaderboardSession session) throws AdventOfCodeException {
        return readSingle(leaderboardKey(session.boardId(), Integer.toString(year), LEADERBOAD_FILE), Leaderboard.class);
    }

    @Override
    public List<ProblemSummary> summaries(final int year) throws AdventOfCodeException {
        try {
            final ListObjectsV2Result result = s3.listObjectsV2(new ListObjectsV2Request()
                    .withBucketName(bucket)
                    .withDelimiter(DELIMITER)
                    .withMaxKeys(MAX_ITEMS)
                    .withPrefix(PROBLEMS_PREFIX + "/" + year + "/"));
            return result.getObjectSummaries().stream()
                    .map(summary -> ProblemSummaryData.builder()
                            .year(year)
                            .day(Integer.parseInt(summary.getKey().split(DELIMITER)[2]))
                            .build())
                    .collect(Collectors.toList());
        } catch (AmazonServiceException ase) {
            LOGGER.error("Failed to list problems under {} {}", bucket, year, ase);
            throw new AdventOfCodeException(ase);
        }
    }

    @Override
    public void addProblem(final Problem problem) {
        putSingle(problemKey(problem), problem);
    }

    @Override
    public void updateLeaders(final LeaderboardSession session, final Leaderboard leaderboard) {
        putSingle(leaderboardKey(session.boardId(), leaderboard.event(), "revisions", System.currentTimeMillis() + ".json"), leaderboard);
        putSingle(leaderboardKey(session.boardId(), leaderboard.event(), LEADERBOAD_FILE), leaderboard);
    }
}
