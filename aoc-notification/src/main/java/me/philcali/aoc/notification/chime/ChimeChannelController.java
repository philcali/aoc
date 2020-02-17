package me.philcali.aoc.notification.chime;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.philcali.aoc.notification.channel.ChannelController;
import me.philcali.aoc.notification.exception.SendMessageException;
import me.philcali.aoc.notification.leaderboard.Leaderboard;
import me.philcali.aoc.notification.leaderboard.Member;
import me.philcali.aoc.notification.leaderboard.Problem;
import me.philcali.http.api.HttpMethod;
import me.philcali.http.api.IHttpClient;
import me.philcali.http.api.IRequest;
import me.philcali.http.api.IResponse;
import me.philcali.http.api.exception.HttpException;

@Singleton
public class ChimeChannelController implements ChannelController<ChimeChannel> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChimeChannelController.class);
    private static final String NEW_PROBLEM_FORMAT = "@Present New Problem %d: %s\n%s";
    private static final String UPDATE_LEADERS_FORMAT = "/md\n@All There has been a change in leaders!\n"
            + "|User|Stars|Score|\n%s";
    private final IHttpClient http;
    private final ObjectMapper mapper;

    @Inject
    public ChimeChannelController(
            final IHttpClient http,
            final ObjectMapper mapper) {
        this.http = http;
        this.mapper = mapper;
    }

    private void sendMessage(final ChimeChannel channel, final String message) {
        try {
            final IRequest request = http.createRequest(HttpMethod.POST, channel.url())
                    .body(mapper.writeValueAsString(new ChimeMessageData(message)))
                    .header("Content-Type", "application/json");
            final IResponse response = request.respond();
            try (final InputStream stream = response.body()) {
                final ChimeResponse data = mapper.readValue(response.body(), ChimeResponse.class);
                LOGGER.info("Successfully posted message {}", data);
            }
        } catch (HttpException e) {
            LOGGER.error("Failed to communicate to chime room", e);
            throw new SendMessageException(e);
        } catch (IOException ie) {
            LOGGER.error("Failed to marshall content for chime room", ie);
            throw new SendMessageException(ie);
        }
    }

    @Override
    public Class<ChimeChannel> channelType() {
        return ChimeChannel.class;
    }

    @Override
    public String metadata() {
        return "chime";
    }

    @Override
    public void sendNewProblem(final ChimeChannel channel, final Problem problem) {
        sendMessage(channel, String.format(NEW_PROBLEM_FORMAT, problem.day(), problem.title(), problem.description()));
    }

    @Override
    public void sendUpdatedLeaders(final ChimeChannel channel, final Leaderboard leaderboard) {
        final List<Member> members = new ArrayList<>(leaderboard.members().values());
        Collections.sort(members, (left, right) -> {
            return Integer.compare(left.localScore(), right.localScore());
        });
        sendMessage(channel, String.format(UPDATE_LEADERS_FORMAT, members.stream()
                .map(member -> "|" + member.name() + "|" + member.stars() + "|" + member.localScore() + "|")
                .collect(Collectors.joining("\n"))));
    }
}
