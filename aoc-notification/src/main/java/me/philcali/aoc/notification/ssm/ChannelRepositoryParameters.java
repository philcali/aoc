package me.philcali.aoc.notification.ssm;

import java.lang.reflect.Proxy;
import java.util.StringJoiner;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathRequest;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;

import me.philcali.aoc.notification.channel.Channel;
import me.philcali.aoc.notification.channel.ChannelMetadata;
import me.philcali.aoc.notification.channel.ChannelMetadataData;
import me.philcali.aoc.notification.channel.ChannelRepository;
import me.philcali.aoc.notification.module.EnvironmentModule;

@Singleton
public class ChannelRepositoryParameters implements ChannelRepository {
    private static final int PER_PAGE = 10;
    private final String pathPrefix;
    private final AWSSimpleSystemsManagement ssm;

    @Inject
    public ChannelRepositoryParameters(
            final AWSSimpleSystemsManagement ssm,
            @Named(EnvironmentModule.CHANNELS_PREFIX) final String pathPrefix) {
        this.ssm = ssm;
        this.pathPrefix = pathPrefix;
    }

    private String stripLast(final Parameter parameter) {
        return parameter.getName().substring(parameter.getName().lastIndexOf('/') + 1);
    }

    @Override
    public Stream<ChannelMetadata> metadata(final String type) {
        return ParameterIterator.stream(ssm, nextToken -> new GetParametersByPathRequest()
                .withNextToken(nextToken)
                .withMaxResults(PER_PAGE)
                .withPath(pathPrefix + "/" + type))
                .map(parameter -> ChannelMetadataData.builder()
                        .type(type)
                        .id(stripLast(parameter))
                        .name(parameter.getValue())
                        .build());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Channel> T load(final ChannelMetadata metadata, final Class<T> channelType) {
        return (T) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] { channelType },
                ParametersInvocationHandlerData.builder()
                        .decryption(true)
                        .ssm(ssm)
                        .pathPrefix(new StringJoiner("/")
                                .add(pathPrefix)
                                .add(metadata.type())
                                .add(metadata.id())
                                .toString())
                        .putProviders("metadata", () -> metadata)
                        .build());
    }
}
