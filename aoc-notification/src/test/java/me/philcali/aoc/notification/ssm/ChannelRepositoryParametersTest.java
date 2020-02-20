package me.philcali.aoc.notification.ssm;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathResult;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;

import me.philcali.aoc.notification.channel.ChannelMetadata;
import me.philcali.aoc.notification.channel.ChannelMetadataData;
import me.philcali.aoc.notification.chime.ChimeChannel;

@RunWith(MockitoJUnitRunner.class)
public class ChannelRepositoryParametersTest {
    private ChannelRepositoryParameters parameters;
    private String pathPrefix;
    @Mock
    private AWSSimpleSystemsManagement ssm;
    @Mock
    private ChannelMetadata metadata;

    @Before
    public void setUp() {
        pathPrefix = "/aoc/channels/";
        parameters = new ChannelRepositoryParameters(ssm, pathPrefix);
        metadata = ChannelMetadataData.builder()
                .name("Test Channel")
                .id("test-channel")
                .type("chime")
                .build();
    }

    @Test
    public void testLoad() {
        ChimeChannel channel = parameters.load(metadata, ChimeChannel.class);
        assertEquals(metadata, channel.metadata());
        GetParameterResult result = new GetParameterResult()
                .withParameter(new Parameter()
                        .withValue("http://example.com"));
        doReturn(result).when(ssm).getParameter(eq(new GetParameterRequest()
                .withWithDecryption(true)
                .withName("/aoc/channels/chime/test-channel/url")));
        assertEquals("http://example.com", channel.url());
    }

    @Test
    public void testMetadata() {
        GetParametersByPathResult firstPage = new GetParametersByPathResult()
                .withParameters(new Parameter()
                        .withName("/aoc/channels/chime/test-channel")
                        .withValue("Test Channel"));
        doReturn(firstPage).when(ssm).getParametersByPath(eq(new GetParametersByPathRequest()
                .withMaxResults(10)
                .withPath("/aoc/channels/chime")));
        assertEquals(Arrays.asList(metadata), parameters.metadata("chime").collect(Collectors.toList()));
        parameters.metadata("chime");
    }
}
