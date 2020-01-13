package me.philcali.aoc.notification.exception;

public class AdventOfCodeException extends RuntimeException {
    private static final long serialVersionUID = 5167502150533652133L;

    public AdventOfCodeException(final Throwable ex) {
        super(ex);
    }

    public AdventOfCodeException(final String message) {
        super(message);
    }
}
