package me.philcali.aoc.notification.chime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.philcali.aoc.notification.MessageService;
import me.philcali.aoc.notification.exception.SendMessageException;
import me.philcali.http.api.HttpMethod;
import me.philcali.http.api.IHttpClient;
import me.philcali.http.api.IRequest;
import me.philcali.http.api.IResponse;
import me.philcali.http.api.exception.HttpException;

@RunWith(MockitoJUnitRunner.class)
public class ChimeMessageServiceTest {
    private MessageService service;
    private String roomUrl;
    private ObjectMapper mapper;
    @Mock
    private IHttpClient http;
    @Mock
    private IRequest request;
    @Mock
    private IResponse response;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        roomUrl = "https://example.com";
        service = new ChimeMessageService(http, mapper, roomUrl);

    }

    @Test
    public void testSend() {
        final String content = "{\"Content\":\"This is a message\"}";
        final String rval = "{\"MessageId\":\"abc-1234\",\"RoomId\":\"roomId\"}";
        final InputStream stream = new ByteArrayInputStream(rval.getBytes(StandardCharsets.UTF_8));
        doReturn(request).when(http).createRequest(eq(HttpMethod.POST), eq(roomUrl));
        doReturn(request).when(request).body(eq(content));
        doReturn(request).when(request).header(eq("Content-Type"), eq("application/json"));
        doReturn(response).when(request).respond();
        doReturn(stream).when(response).body();
        service.send("This is a message");
    }

    @Test(expected = SendMessageException.class)
    public void testHttpFail() {
        final String content = "{\"Content\":\"This is a message\"}";
        doReturn(request).when(http).createRequest(eq(HttpMethod.POST), eq(roomUrl));
        doReturn(request).when(request).body(eq(content));
        doReturn(request).when(request).header(eq("Content-Type"), eq("application/json"));
        doThrow(HttpException.class).when(request).respond();
        service.send("This is a message");
    }
}
