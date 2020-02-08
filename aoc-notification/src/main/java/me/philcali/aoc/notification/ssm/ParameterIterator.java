package me.philcali.aoc.notification.ssm;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathResult;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;

public class ParameterIterator implements Iterator<Parameter> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterIterator.class);

    private final Function<String, GetParametersByPathRequest> nextPage;
    private final AWSSimpleSystemsManagement ssm;
    private String nextToken;
    private Iterator<Parameter> currentPage;

    public ParameterIterator(
            final AWSSimpleSystemsManagement ssm,
            final Function<String, GetParametersByPathRequest> nextPage) {
        this.ssm = ssm;
        this.nextPage = nextPage;
    }

    private synchronized void fillPage() {
        final GetParametersByPathResult result = ssm.getParametersByPath(nextPage.apply(nextToken));
        LOGGER.debug("Filling another parameter page {}", result);
        nextToken = result.getNextToken();
        currentPage = result.getParameters().iterator();
    }

    @Override
    public boolean hasNext() {
        if (Objects.isNull(currentPage) || !currentPage.hasNext()) {
            fillPage();
        }
        return currentPage.hasNext();
    }

    @Override
    public Parameter next() {
        return currentPage.next();
    }

    public static Stream<Parameter> stream(
            final AWSSimpleSystemsManagement ssm,
            final Function<String, GetParametersByPathRequest> nextPage) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        new ParameterIterator(ssm, nextPage), Spliterator.ORDERED), false);
    }
}
