package me.philcali.aoc.notification.module;

import javax.inject.Named;
import javax.inject.Singleton;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;

import dagger.Module;
import dagger.Provides;
import me.philcali.aoc.notification.LeaderboardSessions;
import me.philcali.aoc.notification.ssm.LeaderboardSessionsParameters;

@Module
public class LeaderboardModule {
    @Provides
    @Singleton
    static LeaderboardSessions providesLeaderboardSessions(
            final AWSSimpleSystemsManagement ssm,
            @Named(EnvironmentModule.SESSIONS_PREFIX) final String parameterPrefix) {
        return new LeaderboardSessionsParameters(ssm, parameterPrefix);
    }
}
