package me.philcali.aoc.notification.s3;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpRequestBase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.philcali.aoc.notification.AdventOfCodeStorage;
import me.philcali.aoc.notification.leaderboard.Problem;
import me.philcali.aoc.notification.leaderboard.ProblemData;
import me.philcali.aoc.notification.leaderboard.ProblemSummary;
import me.philcali.aoc.notification.leaderboard.ProblemSummaryData;

@RunWith(MockitoJUnitRunner.class)
public class AdventOfCodeS3Test {
    private AdventOfCodeStorage storage;
    @Mock
    private AmazonS3 s3;
    private ObjectMapper mapper;
    private String bucket;
    private ProblemSummary summary;
    private Problem problem;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        bucket = "bucket";
        storage = new AdventOfCodeS3(bucket, s3, mapper);
        summary = ProblemSummaryData.builder()
                .day(6)
                .year(2019)
                .build();

        problem = ProblemData.builder()
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
    }

    @Test
    public void testDetails() throws JsonProcessingException {
        final S3Object object = mock(S3Object.class);
        doReturn(object).when(s3).getObject(eq(new GetObjectRequest(bucket, "problems/2019/06/problem.json")));
        final byte[] jsonBytes = mapper.writeValueAsBytes(problem);
        doReturn(new S3ObjectInputStream(new ByteArrayInputStream(jsonBytes), mock(HttpRequestBase.class))).when(object).getObjectContent();
        assertEquals(Optional.of(problem), storage.details(summary));
    }

    @Test
    public void testDetailsNotFound() throws JsonProcessingException {
        final AmazonServiceException ase = new AmazonServiceException("not Found");
        ase.setStatusCode(HttpStatus.SC_NOT_FOUND);
        doThrow(ase).when(s3).getObject(eq(new GetObjectRequest(bucket, "problems/2019/06/problem.json")));
        assertEquals(Optional.empty(), storage.details(summary));
    }
}
