package me.philcali.aoc.notification.ssm;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathResult;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;

import me.philcali.aoc.notification.LeaderboardSessions;
import me.philcali.aoc.notification.model.LeaderboardSessionData;

@RunWith(MockitoJUnitRunner.class)
public class LeaderboardSessionsParametersTest {

    private LeaderboardSessions sessions;
    @Mock
    private AWSSimpleSystemsManagement ssm;
    private String parameterPrefix;

    @Before
    public void setUp() {
        parameterPrefix = "/aoc/leaders";
        sessions = new LeaderboardSessionsParameters(ssm, parameterPrefix);
    }

    @Test
    public void testCurrentSessions() {
        GetParametersByPathResult firstPage = new GetParametersByPathResult()
                .withNextToken("abc-123")
                .withParameters(new Parameter()
                        .withName("/aoc/leaders/board1")
                        .withValue("session1"));
        doReturn(firstPage).when(ssm).getParametersByPath(eq(new GetParametersByPathRequest()
                .withMaxResults(10)
                .withWithDecryption(true)
                .withPath(parameterPrefix)));
        GetParametersByPathResult secondPage = new GetParametersByPathResult()
                .withParameters(Collections.emptyList());
        doReturn(secondPage).when(ssm).getParametersByPath(eq(new GetParametersByPathRequest()
                .withMaxResults(10)
                .withWithDecryption(true)
                .withPath(parameterPrefix)
                .withNextToken("abc-123")));
        assertEquals(Arrays.asList(new LeaderboardSessionData("board1", "session1")), sessions.currentSessions().collect(Collectors.toList()));
    }

}
