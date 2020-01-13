package me.philcali.aoc.notification.module;

import javax.inject.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;

import dagger.Module;
import dagger.Provides;
import me.philcali.aoc.notification.AdventOfCode;
import me.philcali.aoc.notification.leaderboard.remote.AdventOfCodeRemote;
import me.philcali.http.api.IHttpClient;

@Module
public class AdventOfCodeModule {
    @Provides
    @Singleton
    static AdventOfCode providesAdventOfCode(final IHttpClient client, final ObjectMapper mapper) {
        return new AdventOfCodeRemote(client, mapper);
    }
}
