package me.philcali.aoc.common;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.function.Function;

public interface AnnotatedDailyEvent extends DailyEvent {
    default <T extends Annotation> int getAnnotationOrFail(final Class<T> annotation, final Function<T, Integer> transform) {
        return Optional.ofNullable(getClass().getAnnotation(annotation))
                .map(transform)
                .orElseThrow(() -> new IllegalStateException(getClass().getSimpleName()
                        + " is not annotated for " + annotation.getSimpleName().toLowerCase()));
    }

    @Override
    default int year() {
        return getAnnotationOrFail(Year.class, Year::value);
    }

    @Override
    default int day() {
        return getAnnotationOrFail(Day.class, Day::value);
    }

    @Override
    default int problem() {
        return getAnnotationOrFail(Problem.class, Problem::value);
    }
}
