package me.philcali.aoc.common;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface AnnotatedDailyEvent extends DailyEvent {

    default <T extends Annotation, R> R getAnnotationOrRecover(final Class<T> annotation, final Function<T, R> transform, final Supplier<R> recover) {
        return Optional.ofNullable(getClass().getAnnotation(annotation))
                .map(transform)
                .orElseGet(recover);
    }


    default <T extends Annotation, R> R getAnnotationOrFail(final Class<T> annotation, final Function<T, R> transform) {
        return Optional.ofNullable(getClass().getAnnotation(annotation))
                .map(transform)
                .orElseThrow(() -> new IllegalStateException(getClass().getSimpleName()
                        + " is not annotated for " + annotation.getSimpleName().toLowerCase()));
    }

    @Override
    default String description() {
        return getAnnotationOrRecover(Description.class, Description::value, () -> "");
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
