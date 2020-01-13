package me.philcali.aoc.notification.chime;

import static me.philcali.aoc.notification.module.EnvironmentModule.ROOM_URL;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.philcali.aoc.notification.MessageService;
import me.philcali.aoc.notification.exception.SendMessageException;
import me.philcali.http.api.HttpMethod;
import me.philcali.http.api.IHttpClient;
import me.philcali.http.api.IRequest;
import me.philcali.http.api.IResponse;
import me.philcali.http.api.exception.HttpException;


public class ChimeMessageService implements MessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChimeMessageService.class);

    private final IHttpClient http;
    private final ObjectMapper mapper;
    private final String roomUrl;

    @Inject
    public ChimeMessageService(
            final IHttpClient http,
            final ObjectMapper mapper,
            @Named(ROOM_URL) final String roomUrl) {
        this.http = http;
        this.mapper = mapper;
        this.roomUrl = roomUrl;
    }

    @Override
    public void send(final String message) {
        try {
            final IRequest request = http.createRequest(HttpMethod.POST, roomUrl)
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
}
