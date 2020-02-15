package me.philcali.aoc.notification.ssm;

import java.util.stream.Stream;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathRequest;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;

import me.philcali.aoc.notification.LeaderboardSessions;
import me.philcali.aoc.notification.model.LeaderboardSession;
import me.philcali.aoc.notification.model.LeaderboardSessionData;
import me.philcali.aoc.notification.module.EnvironmentModule;

@Singleton
public class LeaderboardSessionsParameters implements LeaderboardSessions {
    private static final int MAX_RESULTS = 10;
    private final AWSSimpleSystemsManagement ssm;
    private final String parameterPrefix;

    @Inject
    public LeaderboardSessionsParameters(
            final AWSSimpleSystemsManagement ssm,
           @Named(EnvironmentModule.SESSIONS_PREFIX) final String parameterPrefix) {
        this.ssm = ssm;
        this.parameterPrefix = parameterPrefix;
    }

    private String boardId(final Parameter parameter) {
        return parameter.getName().substring(parameter.getName().lastIndexOf('/') + 1);
    }

    @Override
    public Stream<LeaderboardSession> currentSessions() {
        return ParameterIterator.stream(ssm, token -> new GetParametersByPathRequest()
                .withMaxResults(MAX_RESULTS)
                .withNextToken(token)
                .withWithDecryption(true)
                .withPath(parameterPrefix))
                .map(parameter -> new LeaderboardSessionData(boardId(parameter), parameter.getValue()));
    }
}
